package pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Zwierze;

public final class Wilk extends Zwierze
{
    @Override
    protected Wilk zwrocInstancjeZwierzecia(int x, int y)
    {
        return new Wilk(x, y);
    }

    public static final char IDENTYFIKATOR_PLIKU = 'W';
    public Wilk(int x, int y)
    {
        super(x, y);
        nazwa = "Wilk";
        sila = 9;
        inicjatywa = 5;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }
};