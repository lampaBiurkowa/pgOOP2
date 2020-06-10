package pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.util.Random;

public abstract class Roslina extends Organizm
{
    protected static final int SZANSE_NA_ROZPRZESTRZENIENIE_W_PROCENTACH = 20;
    protected static final int ZASIEG_USTAWIENIA_POTOMKA = 1;
    private static final int DOMYSLNY_KROK = 1;
    protected abstract Roslina zwrocInstancjeRosliny(int x, int y);

    public Roslina(int x, int y)
    {
        super(x, y);
        inicjatywa = 0;
    }

    @Override
    public void Akcja(Swiat swiat)
    {
        Random losownie = new Random();
        if (czyMaGdzieUstawicPotomka(swiat) && losownie.nextInt(100) < SZANSE_NA_ROZPRZESTRZENIENIE_W_PROCENTACH)
        {
            boolean czyUdaloSieZasiac = false;
            while (!czyUdaloSieZasiac)
                czyUdaloSieZasiac = sprobujZasiacRosline(swiat);

            swiat.DodajKomunikat(nazwa + " zasialo brata");
        }
    }

    protected boolean czyMaGdzieUstawicPotomka(Swiat swiat)
    {
        for (int i = -ZASIEG_USTAWIENIA_POTOMKA; i <= ZASIEG_USTAWIENIA_POTOMKA; i++)
            for (int j = -ZASIEG_USTAWIENIA_POTOMKA; j <= ZASIEG_USTAWIENIA_POTOMKA; j++)
            {
                if ((i == 0 && j == 0) || (i != 0 && j != 0) || !swiat.CzyPunktMiesciSieNaMapie(x + j, y + i))
                    continue;

                if (!swiat.CzyPoleZajete(x + j, y + i) || !swiat.CzyOrganizmJestNaPolu(x + j, y + i, this.getClass()))
                    return true;
            }

        return false;
    }

    protected boolean sprobujZasiacRosline(Swiat swiat)
    {
        Random losowanie = new Random();
        int zmianaX = losowanie.nextInt(2 * Roslina.DOMYSLNY_KROK + 1) - Roslina.DOMYSLNY_KROK;
        int zmianaY = losowanie.nextInt(2 * Roslina.DOMYSLNY_KROK + 1) - Roslina.DOMYSLNY_KROK;
        if (zmianaX == 0 && zmianaY == 0)
            return false;

        if (x + zmianaX < 0 || x + zmianaX >= swiat.GetSzerokosc() || y + zmianaY < 0 || y + zmianaY >= swiat.GetWysokosc())
            return false;

        Roslina roslina = zwrocInstancjeRosliny(x + zmianaX, y + zmianaY);
        swiat.DodajOrganizm(roslina);
        return true;
    }
};