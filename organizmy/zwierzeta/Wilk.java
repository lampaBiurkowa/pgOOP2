package pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Zwierze;

import java.awt.*;

public final class Wilk extends Zwierze
{
    public static final char IDENTYFIKATOR_PLIKU = 'W';

    @Override
    protected Wilk zwrocInstancjeZwierzecia(int x, int y)
    {
        return new Wilk(x, y);
    }

    public Wilk(int x, int y)
    {
        super(x, y);
        kolorPola = new Color(100, 100, 0);
        nazwa = "Wilk";
        sila = 9;
        inicjatywa = 5;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }
};