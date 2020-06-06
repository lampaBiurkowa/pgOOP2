package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;

import javax.swing.*;
import java.awt.*;

public final class Mapa
{
    private static final int BOK_POLA = 20;
    private JLabel pola[][];

    public Mapa(JFrame okno, Swiat swiat)
    {
        pola = new JLabel[swiat.GetWysokosc()][swiat.GetSzerokosc()];
        for (int i = 0; i < swiat.GetWysokosc(); i++)
            for (int j = 0; j < swiat.GetSzerokosc(); j++)
            {
                pola[i][j] = new JLabel(" ", SwingConstants.CENTER);
                pola[i][j].setBackground(Color.WHITE);
                pola[i][j].setSize(BOK_POLA, BOK_POLA);
                pola[i][j].setOpaque(true);
                pola[i][j].setBounds(j * BOK_POLA, i * BOK_POLA, BOK_POLA, BOK_POLA);
                okno.add(pola[i][j]);
            }

        okno.setSize(BOK_POLA * (swiat.GetSzerokosc() + 2), BOK_POLA * (swiat.GetWysokosc() + 2));
    }

    public JLabel GetPole(int x, int y)
    {
        return pola[y][x];
    }
}