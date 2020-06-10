package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Czlowiek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class ObslugaRuchu
{
    private static final int WYSOKOSC_OKNA = 100;
    private static final int SZEROKOSC_OKNA = 300;
    private static final int X_OKNA = 800;
    private static final int Y_OKNA = 100;

    private JFrame okno;
    private JButton lewo;
    private JButton prawo;
    private JButton gora;
    private JButton dol;
    private Kierunek wybor;

    public ObslugaRuchu(Swiat swiat, Czlowiek czlowiek)
    {
        wybor = Kierunek.BRAK;
        okno = new JFrame("Kieruek");
        okno.setLayout(new FlowLayout());
        okno.setBounds(X_OKNA, Y_OKNA, SZEROKOSC_OKNA, WYSOKOSC_OKNA);
        dodajStrzalki(swiat, czlowiek);
        okno.setVisible(true);
    }

    private void dodajStrzalki(Swiat swiat, Czlowiek czlowiek)
    {
        dodajStrzalkeWLewo(swiat, czlowiek);
        dodajStrzalkeWPrawo(swiat, czlowiek);
        dodajStrzalkeWDol(swiat, czlowiek);
        dodajStrzalkeWGore(swiat, czlowiek);
    }

    private void dodajStrzalkeWLewo(Swiat swiat, Czlowiek czlowiek)
    {
        lewo = new JButton("<-");
        if (czlowiek.GetX() == 0)
            lewo.setEnabled(false);
        lewo.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                wybor = Kierunek.LEWO;
            }
        });
        okno.add(lewo);
    }

    private void dodajStrzalkeWPrawo(Swiat swiat, Czlowiek czlowiek)
    {
        prawo = new JButton("->");
        if (czlowiek.GetX() == swiat.GetSzerokosc() - 1)
            prawo.setEnabled(false);
        prawo.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                wybor = Kierunek.PRAWO;
            }
        });
        okno.add(prawo);
    }

    private void dodajStrzalkeWGore(Swiat swiat, Czlowiek czlowiek)
    {
        gora = new JButton("/\\");
        if (czlowiek.GetY() == 0)
            gora.setEnabled(false);
        gora.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                wybor = Kierunek.GORA;
            }
        });
        okno.add(gora);
    }

    private void dodajStrzalkeWDol(Swiat swiat, Czlowiek czlowiek)
    {
        dol = new JButton("\\/");
        if (czlowiek.GetY() == swiat.GetWysokosc() - 1)
            dol.setEnabled(false);
        dol.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                wybor = Kierunek.DOL;
            }
        });
        okno.add(dol);
    }

    public Kierunek CzekajNaWybor()
    {
        while (true)
        {
            try
            {
                wait(100);
            } catch (Exception e) { }

            if (wybor != Kierunek.BRAK)
            {
                okno.setVisible(false);
                okno = null;
                return wybor;
            }
        }
    }
}