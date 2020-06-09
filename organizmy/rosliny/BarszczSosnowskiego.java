package pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Cyberowca;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Zwierze;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.awt.*;

public final class BarszczSosnowskiego extends Roslina
{
    private static final int ZASIEG_ZNISZCZEN = 1;

    @Override
    protected BarszczSosnowskiego zwrocInstancjeRosliny(int x, int y)
    {
        return new BarszczSosnowskiego(x, y);
    }
    public static final char IDENTYFIKATOR_PLIKU = 'b';

    public BarszczSosnowskiego(int x, int y)
    {
        super(x, y);
        nazwa = "Barszcz Sosnowskiego";
        sila = 10;
        znakASCII = IDENTYFIKATOR_PLIKU;
        kolorPola = Color.BLUE;
    }

    @Override
    public void Akcja(Swiat swiat)
    {
        for (int i = -ZASIEG_ZNISZCZEN; i <= ZASIEG_ZNISZCZEN; i++)
            for (int j = -ZASIEG_ZNISZCZEN; j <=ZASIEG_ZNISZCZEN ; j++)
            {
                if ((i == 0 && j == 0) || (i != 0 && j != 0) || !swiat.CzyPunktMiesciSieNaMapie(x + j, y + i))
                    continue;

                if (!swiat.CzyPoleZajete(x + i, y + j))
                    continue;

                Organizm organizm = swiat.GetOrganizmNaPozycji(x + i, y + j);
                if (!(organizm instanceof Zwierze) || organizm instanceof Cyberowca)
                    continue;

                swiat.DodajKomunikat(nazwa + " zabija " + organizm.GetNazwa());
                swiat.UsunOrganizm(organizm);
            }
    }

    @Override
    public void Kolizja(Swiat swiat, Organizm organizm)
    {
        if (!(organizm instanceof Zwierze) || organizm instanceof Cyberowca)
            return;

        swiat.DodajKomunikat(organizm.GetNazwa() + " zjadl " + nazwa + " i nie zyje");
        swiat.UsunOrganizm(organizm);
    }
};