package pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Zwierze;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

public final class WilczeJagody extends Roslina
{
    @Override
    protected WilczeJagody zwrocInstancjeRosliny(int x, int y)
    {
        return new WilczeJagody(x, y);
    }

    public static final char IDENTYFIKATOR_PLIKU = 'w';

    public WilczeJagody(int x, int y)
    {
        super(x, y);
        nazwa = "Wilcze Jagody";
        sila = 99;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }

    @Override
    public void Kolizja(Swiat swiat, Organizm organizm)
    {
        if (!(organizm instanceof Zwierze))
            return;

        swiat.DodajKomunikat(organizm.GetNazwa() + " zjadl " + nazwa + " i nie zyje");
        swiat.UsunOrganizm(organizm);
    }
};