package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny.BarszczSosnowskiego;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny.Guarana;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny.Mlecz;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny.Trawa;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class PanelDodawania
{
    private JFrame okno;
    private JButton[] przyciski;

    private final int WYSOKOSC_PRZYCISKU = 30;
    private final int SZEROKOSC_PRZYCISKU = 150;

    public PanelDodawania(Swiat swiat, int x, int y)
    {
        okno = new JFrame("Dodaj organizm");

        okno.setVisible(true);
        Organizm[] organizmy = {new Antylopa(x, y), new BarszczSosnowskiego(x, y), new Cyberowca(x, y), new Guarana(x, y),
        new Lis(x, y), new Mlecz(x, y), new Owca(x, y), new Trawa(x, y), new Wilk(x, y), new Zolw(x, y)};

        for (int i = 0; i < organizmy.length; i++)
        {
            przyciski[i] = new JButton(organizmy[i].GetNazwa());
            przyciski[i].setBounds(0, i * WYSOKOSC_PRZYCISKU, SZEROKOSC_PRZYCISKU, WYSOKOSC_PRZYCISKU);
            okno.add(przyciski[i]);
            int kopiaI = i;
            przyciski[i].addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent event)
                {
                    swiat.DodajOrganizm(organizmy[kopiaI]);
                    schowaj();
                    swiat.SetOczekujeNaDodanie(false);
                }
            });
        }
    }

    private void schowaj()
    {
        okno.setVisible(false);
        przyciski = null;
        okno = null;
    }
}