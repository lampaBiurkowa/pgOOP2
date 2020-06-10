package pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.awt.*;
import java.util.Random;

public final class Antylopa extends Zwierze
{
    public static final char IDENTYFIKATOR_PLIKU = 'A';
    private static final int KROK_UCIECZKI = 1;
    private static final int SZANSA_NA_UCIECZKE_W_PROCENTACH = 50;

    @Override
    protected Antylopa zwrocInstancjeZwierzecia(int x, int y)
    {
        return new Antylopa(x, y);
    }


    public Antylopa(int x, int y)
    {
        super(x, y);
        kolorPola = new Color(100, 50, 0);
        nazwa = "Antylopa";
        inicjatywa = 4;
        sila = 4;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }

    @Override
    public void Akcja(Swiat swiat)
    {
        previousX = x;
        previousY = y;
        boolean czyUdaloSiePoruszyc = false;
        while (!czyUdaloSiePoruszyc)
            czyUdaloSiePoruszyc = sprubojWykonacRuch(swiat, 2);
    }

    @Override
    public void Kolizja(Swiat swiat, Organizm organizm)
    {
        super.Kolizja(swiat, organizm);

        if (organizm instanceof Zwierze)
            return;

        Random losowanie = new Random();
        boolean czyMozeUciec = losowanie.nextInt(100 / SZANSA_NA_UCIECZKE_W_PROCENTACH) == 1;
        if (czyMozeUciec && czyMaGdzieUciec(swiat))
        {
            boolean ucieczkaUdana = false;
            while (!ucieczkaUdana)
                ucieczkaUdana = sprobujUciec(swiat);

            swiat.DodajKomunikat(nazwa + " ucieka przed " + organizm.GetNazwa());
        }
    }

    private boolean czyMaGdzieUciec(Swiat swiat)
    {
        for (int i = -KROK_UCIECZKI; i <= KROK_UCIECZKI; i++)
            for (int j = -KROK_UCIECZKI; j <= KROK_UCIECZKI; j++)
            {
                if ((i == 0 && j == 0) || (i != 0 && j != 0) || !swiat.CzyPunktMiesciSieNaMapie(previousX + j, previousY + i))
                    continue;

                if (!swiat.CzyPoleZajete(previousX + j, previousY + i))
                    return true;
            }

        return false;
    }

    private boolean sprobujUciec(Swiat swiat)
    {
        Random losowanie = new Random();
        int zmianaX = losowanie.nextInt(KROK_UCIECZKI * 2 + 1) - KROK_UCIECZKI;
        int zmianaY = losowanie.nextInt(KROK_UCIECZKI * 2 + 1) - KROK_UCIECZKI;

        if ((zmianaX == 0 && zmianaY == 0) || (zmianaX != 0 && zmianaY != 0))
            return false;

        if (swiat.CzyPoleZajete (x + zmianaX, y + zmianaY))
            return false;

        return SprubojPrzesunacO(zmianaX, zmianaY, swiat);
    }
};