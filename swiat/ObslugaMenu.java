package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Czlowiek;

import javax.swing.*;
import java.io.File;

public final class ObslugaMenu
{
    private Swiat swiat;
    private BudulecSwiata budulec;
    private JFrame okno;

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
        swiat.RysujSwiat(okno);
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
        File f = new File("C:/ngruza1/Java/studia/proj/la2/" + nazwaPliku)
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
        int x = 0, y = 0, iloscSztuk = 0; //TODO
        //cout<<"Podaj szerokosc"<<endl;
        //cin>>x;
        //cout<<"Podaj wysokosc"<<endl;
        //cin>>y;
        //cout<<"Podaj poczatkowa ilosc sztuk organizmow (oprocz czlowieka)"<<endl;
        //cin>>iloscSztuk;
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
        okno = new JFrame();
        swiat.RysujSwiat(okno);
        while (true)
        {
            obslozTure();
            int odpowiedz = JOptionPane.showConfirmDialog (okno, "Czy zapisac swiat do pliku?","Zapisac?", JOptionPane.YES_NO_OPTION);
            if (odpowiedz == JOptionPane.NO_OPTION)
                break;
        }
    }
};