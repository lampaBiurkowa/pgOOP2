package pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny.Roslina;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.awt.*;

public final class Trawa extends Roslina
{
    public static final char IDENTYFIKATOR_PLIKU = 't';

    @Override
    protected Trawa zwrocInstancjeRosliny(int x, int y)
    {
        return new Trawa(x, y);
    }

    public Trawa(int x, int y)
    {
        super(x, y);
        kolorPola = Color.GREEN;
        nazwa = "Trawa";
        sila = 0;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }

    @Override
    public void Kolizja(Swiat swiat, Organizm organizm)
    {
    }
};