package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;

import javax.swing.*;

public final class Mapa
{
    private static final int BOK_POLA = 40;
    private JLabel pola[][];

    public Mapa(JFrame okno, Swiat swiat)
    {
        pola = new JLabel[swiat.GetWysokosc()][swiat.GetSzerokosc()];
        for (int i = 0; i < swiat.GetWysokosc(); i++)
            for (int j = 0; j < swiat.GetSzerokosc(); j++)
            {
                pola[i][j] = new JLabel(" ");
                pola[i][j].setSize(BOK_POLA, BOK_POLA);
                pola[i][j].setBounds(j * Organizm.BOK_KWADRATU_OZNACZENIA, i * Organizm.BOK_KWADRATU_OZNACZENIA, Organizm.BOK_KWADRATU_OZNACZENIA, Organizm.BOK_KWADRATU_OZNACZENIA);
                okno.add(pola[i][j]);
            }

        okno.setSize(BOK_POLA * swiat.GetSzerokosc(), BOK_POLA * swiat.GetWysokosc());
    }

    public JLabel GetPole(int x, int y)
    {
        return pola[y][x];
    }
}