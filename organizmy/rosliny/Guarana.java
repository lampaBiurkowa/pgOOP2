package pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Zwierze;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.awt.*;

public final class Guarana extends Roslina
{
    private static final int ZWIEKSZENIE_SILY = 3;

    @Override
    protected Guarana zwrocInstancjeRosliny(int x, int y)
    {
        return new Guarana(x, y);
    }
    public static final char IDENTYFIKATOR_PLIKU = 'g';

    public Guarana(int x, int y)
    {
        super(x, y);
        kolorPola = Color.CYAN;
        nazwa = "Guarana";
        sila = 0;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }

    @Override
    public void Kolizja(Swiat swiat, Organizm organizm)
    {
        if (organizm instanceof Zwierze)
        {
            organizm.SetSila(organizm.GetSila() + ZWIEKSZENIE_SILY);
            ((Zwierze)(organizm)).OznaczZwiekszenieSily(ZWIEKSZENIE_SILY);
            swiat.DodajKomunikat(((Zwierze)(organizm)).GetNazwa() + " zjadl " + nazwa + " i jego sila zwiekszyla sie o 3");
        }
    }
};