package pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny.Roslina;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.awt.*;
import java.util.Random;

public final class Mlecz extends Roslina
{
    public static final char IDENTYFIKATOR_PLIKU = 'm';
    private static final int ILOSC_PROB_ROZPRZESTRZENIENIA = 3;
    private static final int KROK_ROZPRZESTRZENIANIA = 1;
    
    protected Mlecz zwrocInstancjeRosliny(int x, int y)
    {
        return new Mlecz(x, y);
    }


    public Mlecz(int x, int y)
    {
        super(x, y);
        kolorPola = new Color(0, 200, 100);
        nazwa = "Mlecz";
        sila = 0;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }

    @Override
    public void Akcja(Swiat swiat)
    {
        int[][] uzyteMiejsca = new int[ILOSC_PROB_ROZPRZESTRZENIENIA][];
        for (int i = 0; i < ILOSC_PROB_ROZPRZESTRZENIENIA; i++)
            uzyteMiejsca[i] = new int[]{-1, -1};

        Random losowanie = new Random();
        for (int i = 0; i < ILOSC_PROB_ROZPRZESTRZENIENIA; i++)
            if (czyMaGdzieUstawicPotomka(swiat) && losowanie.nextInt(100) < SZANSE_NA_ROZPRZESTRZENIENIE_W_PROCENTACH)
            {
                boolean czyUdaloSieZasiac = false;
                while (!czyUdaloSieZasiac)
                    czyUdaloSieZasiac = sprobujZasiacRosline(swiat, uzyteMiejsca);

                uzyteMiejsca[i][0] = swiat.GetOrganizmy()[swiat.GetIloscOrganizmow() - 1].GetX();
                uzyteMiejsca[i][1] = swiat.GetOrganizmy()[swiat.GetIloscOrganizmow() - 1].GetY();
                swiat.DodajKomunikat(nazwa + " zasialo brata");
            }

        uzyteMiejsca = null;
    }

    private boolean sprobujZasiacRosline(Swiat swiat, int[][] uzyteMiejsca)
    {
        Random losowanie = new Random();
        int zmianaX = losowanie.nextInt(2 * KROK_ROZPRZESTRZENIANIA + 1) - KROK_ROZPRZESTRZENIANIA;
        int zmianaY = losowanie.nextInt(2 * KROK_ROZPRZESTRZENIANIA + 1) - KROK_ROZPRZESTRZENIANIA;
        if (zmianaX == 0 && zmianaY == 0)
            return false;

        if (!swiat.CzyPunktMiesciSieNaMapie(x + zmianaX, y + zmianaY) || swiat.CzyOrganizmJestNaPolu(x + zmianaX, y + zmianaY, this.getClass()))
            return false;

        for (int i = 0; i < ILOSC_PROB_ROZPRZESTRZENIENIA; i++)
            if (uzyteMiejsca[i][0] == x + zmianaX && uzyteMiejsca[i][1] == y + zmianaY)
                return false;

        Roslina roslina = zwrocInstancjeRosliny(x + zmianaX, y + zmianaY);
        swiat.DodajOrganizm(roslina);
        return true;
    }

    @Override
    public void Kolizja(Swiat swiat, Organizm organizm)
    {
    }
};
