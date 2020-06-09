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
    private Mapa mapa;
    
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

        char wybor = '\0';

        //cout<<"Twoja sila: "<<czlowiek.GetSila()<<endl;
        //cout<<"Pozostala ilosc tur z wazna supermoca: "<<czlowiek.GetPozostalaIloscTurZSupermoca()<<endl;
        //cout<<"Ilosc tur do ponownego aktywowania supermocy: "<<czlowiek.GetIloscTurDoUzyciaSupermocy() + czlowiek.GetPozostalaIloscTurZSupermoca()<<endl;
        if (czlowiek.GetIloscTurDoUzyciaSupermocy() == 0)
        {
            //cout<<"Czy aktywowac supermoc? (t/N)";
            //cin>>wybor;
            if (wybor == 't')
                czlowiek.SprobujAktywowacSuperMoc();
        }
        //cout<<"Wybierz strzalka kierunek ruchu"<<endl;
        czlowiek.WczytajInformacjeORuchu(swiat);
    }

    private void obslozSymulacje()
    {
        swiat.WykonajTure();
        swiat.RysujSwiat(mapa);
    }

    private void obslozTure()
    {
        //cout<<"Numer tury: "<<swiat.GetNumerTury() + 1<<endl;
        obslozRuchCzlowiekaJezeliIstnieje();
        obslozSymulacje();
        obslozZapisDoPliku();
    }

    private void obslozWczytanieSwiataZPliku()
    {
        String nazwaPliku = JOptionPane.showInputDialog(null, "Podaj nazwę pliku");
        File f = new File("C:/ngruza1/Java/studia/proj/la2/" + nazwaPliku);
        try
        {
            budulec.WczytajZPliku(swiat, "C:/ngruza1/Java/studia/proj/la2/" + nazwaPliku);
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
        swiat.Stworz(x, y, 0);
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
        budulec = new BudulecSwiata();
        swiat = new Swiat();
        obslozInicjalizacjeSwiata();
        obsluzInicjalizcjeOkna();
        mapa = new Mapa(okno, swiat);
        swiat.RysujSwiat(mapa);
        czyPrzejscDoNastepnejTury = true;
        while (true)
        {
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