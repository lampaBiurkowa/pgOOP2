package pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny.BarszczSosnowskiego;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public final class Cyberowca extends Zwierze
{
    @Override
    protected Cyberowca zwrocInstancjeZwierzecia(int x, int y)
    {
        return new Cyberowca(x, y);
    }

    public static final char IDENTYFIKATOR_PLIKU = 'X';

    public Cyberowca(int x, int y)
    {
        super(x, y);
        kolorPola = new Color(100, 150, 200);
        nazwa = "Cyberowca";
        inicjatywa = 4;
        sila = 11;
        znakASCII = IDENTYFIKATOR_PLIKU;
    }

    @Override
    public void Akcja(Swiat swiat)
    {
        ArrayList<Integer> xBarszczow = new ArrayList<>();
        ArrayList<Integer> yBarszczow = new ArrayList<>();
        for (int i = 0; i < swiat.GetWysokosc(); i++)
            for (int j = 0; j < swiat.GetSzerokosc(); j++)
                if (swiat.CzyOrganizmJestNaPolu(j, i, BarszczSosnowskiego.class))
                {
                    xBarszczow.add(j);
                    yBarszczow.add(i);
                }

        if (xBarszczow.size() > 0)
            przesunWStroneBarszczu(xBarszczow, yBarszczow, swiat);
    }

    private int getIndeksNajblizszegoBarszczu(ArrayList<Integer> xBarszczow, ArrayList<Integer> yBarszczow)
    {
        int indeksNajblizszehoBarszczu = -1;
        double najmniejszyDystans = -1;
        for (int i = 0; i < xBarszczow.size(); i++)
        {
            double dystans = Math.sqrt(Math.pow(xBarszczow.get(i) - x, 2) + Math.pow(yBarszczow.get(i) - y, 2));
            if (dystans < najmniejszyDystans || najmniejszyDystans == -1)
            {
                najmniejszyDystans = dystans;
                indeksNajblizszehoBarszczu = i;
            }
        }

        return indeksNajblizszehoBarszczu;
    }

    private void przesunWStroneBarszczu(ArrayList<Integer> xBarszczow, ArrayList<Integer> yBarszczow, Swiat swiat)
    {
        int indeksNajblizszehoBarszczu = getIndeksNajblizszegoBarszczu(xBarszczow, yBarszczow);
        if (xBarszczow.get(indeksNajblizszehoBarszczu) == x && xBarszczow.get(indeksNajblizszehoBarszczu) == y)
            return;
        else if (xBarszczow.get(indeksNajblizszehoBarszczu) == x)
            przesunWOsiY(yBarszczow.get(indeksNajblizszehoBarszczu), swiat);
        else if (yBarszczow.get(indeksNajblizszehoBarszczu) == y)
            przesunWOsiX(xBarszczow.get(indeksNajblizszehoBarszczu), swiat);
        else
        {
            Random losowanie = new Random();
            if (losowanie.nextInt(2) == 1)
                przesunWOsiX(xBarszczow.get(indeksNajblizszehoBarszczu), swiat);
            else
                przesunWOsiY(yBarszczow.get(indeksNajblizszehoBarszczu), swiat);
        }
    }

    private void przesunWOsiX(int xNajblizszegoBarszczu, Swiat swiat)
    {
        if (xNajblizszegoBarszczu > x)
            SprubojPrzesunacO(DOMYSLNY_KROK, 0, swiat);
        else
            SprubojPrzesunacO(-DOMYSLNY_KROK, 0, swiat);
    }

    private void przesunWOsiY(int yNajblizszegoBarszczu, Swiat swiat)
    {
        if (yNajblizszegoBarszczu > y)
            SprubojPrzesunacO(0, DOMYSLNY_KROK, swiat);
        else
            SprubojPrzesunacO(0, -DOMYSLNY_KROK, swiat);
    }
};