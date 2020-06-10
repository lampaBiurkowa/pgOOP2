package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Czlowiek;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public final class ObslugaMenu
{
    private Swiat swiat;
    private BudulecSwiata budulec;
    private JFrame okno;
    private JButton przyciskNastepnejTury;
    private JButton przyciskZakonczenia;
    
    private boolean czyPrzejscDoNastepnejTury;
    public static final int WYSOKOSC_PRZYCISKU_MENU = 30;
    public static final int SZEROKOSC_PRZYCISKU_MENU = 150;

    private void obslozInicjalizacjeSwiata()
    {
        int odpowiedz = JOptionPane.showConfirmDialog (null, "Wczytac swiat z pliku czy wygenerowac losowy","Inicjalizacja", JOptionPane.YES_NO_OPTION);
        if (odpowiedz == JOptionPane.YES_OPTION)
            obslozWczytanieSwiataZPliku();
        else
            obslozWygenerowanieSwiata();
    }

    private void obslozRuchCzlowiekaJezeliIstnieje()
    {
        Czlowiek czlowiek = swiat.SprobujZnalezcCzlowieka();
        if (czlowiek == null)
            return;

        JOptionPane.showMessageDialog(okno, getTekstInformacji(czlowiek), "Stan gry", JOptionPane.INFORMATION_MESSAGE);
        if (czlowiek.GetIloscTurDoUzyciaSupermocy() == 0)
        {
            int wybor = JOptionPane.showConfirmDialog(okno, "Czy aktywować supermoc?", "Supermoc", JOptionPane.YES_NO_OPTION);
            if (wybor == JOptionPane.YES_OPTION)
                czlowiek.SprobujAktywowacSuperMoc();
        }
        czlowiek.WczytajInformacjeORuchu(swiat);
    }

    private String getTekstInformacji(Czlowiek czlowiek)
    {
        String informacjeSila = "Twoja siła: " + czlowiek.GetSila();
        String informacjeTuryZSupermoca = "\nPozostala ilosc tur z wazna supermoca: " + czlowiek.GetPozostalaIloscTurZSupermoca();
        String informacjePonownaAktywacja = "\nIlosc tur do ponownego aktywowania supermocy: " + (czlowiek.GetIloscTurDoUzyciaSupermocy() + czlowiek.GetPozostalaIloscTurZSupermoca());
        return informacjeSila + informacjeTuryZSupermoca + informacjePonownaAktywacja;
    }

    private void obslozSymulacje()
    {
        swiat.WykonajTure();
        swiat.RysujSwiat();
    }

    private void obslozTure()
    {
        obslozRuchCzlowiekaJezeliIstnieje();
        obslozSymulacje();
        obslozZapisDoPliku();
    }

    private void obslozWczytanieSwiataZPliku()
    {
        String nazwaPliku = JOptionPane.showInputDialog(null, "Podaj nazwę pliku");
        File f = new File(nazwaPliku);
        try
        {
            budulec.WczytajZPliku(swiat, nazwaPliku);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void obslozWygenerowanieSwiata()
    {
        int x = Integer.parseInt(JOptionPane.showInputDialog("Podaj szerokosc"));
        int y = Integer.parseInt(JOptionPane.showInputDialog("Podaj wysokosc"));
        int iloscSztuk = Integer.parseInt(JOptionPane.showInputDialog("Podaj poczatkowa ilosc sztuk organizmow (oprocz czlowieka)"));
        swiat.Stworz(x, y, 0, okno);
        budulec.RozstawOrganizmyLosowo(swiat, iloscSztuk);
    }

    private void obslozZapisDoPliku()
    {
        int odpowiedz = JOptionPane.showConfirmDialog (okno, "Czy zapisac swiat do pliku?","Zapisac?", JOptionPane.YES_NO_OPTION);
        if (odpowiedz == JOptionPane.YES_OPTION)
        {
            String nazwaPliku = JOptionPane.showInputDialog(okno, "Podaj nazwę pliku");
            budulec.ZapiszDoPliku(swiat, nazwaPliku);
        }
    }

    public void Wykonaj()
    {
        obsluzInicjalizcjeOkna();
        budulec = new BudulecSwiata(okno);
        swiat = new Swiat();
        obslozInicjalizacjeSwiata();
        swiat.RysujSwiat();
        czyPrzejscDoNastepnejTury = false;
        while (true)
        {
            try {
                wait(100);
            } catch (Exception e) {
            }
            if (!swiat.GetOczekujeNaDodanie() && czyPrzejscDoNastepnejTury)
            {
                przyciskNastepnejTury.setEnabled(false);
                obslozTure();
                czyPrzejscDoNastepnejTury = false;
                przyciskNastepnejTury.setEnabled(true);
            }
        }
    }


    private void obsluzInicjalizcjeOkna()
    {
        okno = new JFrame();
        okno.setVisible(true);
        okno.setLayout(null);

        obsluzInicjalizacjePaskaMenu();

        okno.add(przyciskNastepnejTury);
        okno.add(przyciskZakonczenia);
    }

    private void obsluzInicjalizacjePaskaMenu()
    {
        przyciskNastepnejTury = new JButton("Następna tura");
        przyciskNastepnejTury.setBounds(0, 0, SZEROKOSC_PRZYCISKU_MENU, WYSOKOSC_PRZYCISKU_MENU);
        przyciskNastepnejTury.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent event)
            {
                czyPrzejscDoNastepnejTury = true;
            }
        });
        przyciskZakonczenia = new JButton("Zakończ");
        przyciskZakonczenia.setBounds(SZEROKOSC_PRZYCISKU_MENU, 0, SZEROKOSC_PRZYCISKU_MENU, WYSOKOSC_PRZYCISKU_MENU);
        przyciskZakonczenia.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent event)
            {
                okno.setVisible(false);
                System.exit(0);
            }
        });
    }
};