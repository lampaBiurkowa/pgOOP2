package pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.awt.*;

public final class Lis extends Zwierze
{
    public static final char IDENTYFIKATOR_PLIKU = 'L';

    @Override
    protected Lis zwrocInstancjeZwierzecia(int x, int y)
    {
        return new Lis(x, y);
    }

    public Lis(int x, int y)
    {
        super(x, y);
        kolorPola = Color.ORANGE;
        nazwa = "Lis";
        inicjatywa = 7;
        sila = 3;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }

    @Override
    public void Akcja(Swiat swiat)
    {
        previousX = x;
        previousY = y;

        if (!czyMozeSieRuszyc(swiat))
            return;

        do
        {
            Cofnij();
            boolean czySiePoruszyl = false;
            while (!czySiePoruszyl)
                czySiePoruszyl = sprubojWykonacRuch(swiat, DOMYSLNY_KROK);
        } while (swiat.CzyPoleZajete(x, y) && swiat.GetOrganizmNaPozycji(x, y).GetSila() > sila);
    }

    private boolean czyMozeSieRuszyc(Swiat swiat)
    {
        final int KROK = 1;
        for (int i = -KROK; i <= KROK; i++)
            for (int j = -KROK; j <= KROK; j++)
            {
                if ((i == 0 && j == 0) || (i != 0 && j != 0) || !swiat.CzyPunktMiesciSieNaMapie(previousX + j, previousY + i))
                    continue;

                if (!swiat.CzyPoleZajete(previousX + j, previousY + i))
                    return true;
                else if (swiat.GetOrganizmNaPozycji(previousX + j, previousY + i).GetSila() <= sila)
                    return true;
            }

        return false;
    }
};