package pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Zwierze;

public final class Owca extends Zwierze
{
    @Override
    protected Owca zwrocInstancjeZwierzecia(int x, int y)
    {
        return new Owca(x, y);
    }
    public static final char IDENTYFIKATOR_PLIKU = 'O';

    public Owca(int x, int y)
    {
        super(x, y);
        nazwa = "Owca";
        inicjatywa = 4;
        sila = 4;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }
};