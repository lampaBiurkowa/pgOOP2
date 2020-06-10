package pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Zwierze;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.awt.*;
import java.util.Random;

public final class Zolw extends Zwierze
{
    public static final char IDENTYFIKATOR_PLIKU = 'Z';
    private static final int MAKSYMALNA_SILA_DO_ODPARCIA = 4;
    private static final int SZANSE_NA_POZOSTANIE_W_MIEJSCU_W_PROCENTACH = 75;

    @Override
    protected Zolw zwrocInstancjeZwierzecia(int x, int y)
    {
        return new Zolw(x, y);
    }

    public Zolw(int x, int y)
    {
        super(x, y);
        kolorPola = Color.RED;
        nazwa = "Zolw";
        inicjatywa = 1;
        sila = 2;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }

    @Override
    public void Akcja(Swiat swiat)
    {
        Random losowanie = new Random();
        boolean czyStoiWMiejscu = losowanie.nextInt(100) < SZANSE_NA_POZOSTANIE_W_MIEJSCU_W_PROCENTACH;
        if (czyStoiWMiejscu)
            return;

        boolean czySiePoruszyl = false;
        while (!czySiePoruszyl)
            czySiePoruszyl = sprubojWykonacRuch(swiat, DOMYSLNY_KROK);
    }

    @Override
    public void Kolizja(Swiat swiat, Organizm organizm)
    {
        super.Kolizja(swiat, organizm);
        if (organizm instanceof Zwierze)
            if (organizm.GetSila() <= MAKSYMALNA_SILA_DO_ODPARCIA)
            {
                ((Zwierze)(organizm)).Cofnij();
                swiat.DodajKomunikat(nazwa + " odpiera atak " + organizm.GetNazwa());
            }
    }
};