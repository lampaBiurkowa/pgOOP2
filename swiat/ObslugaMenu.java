package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Czlowiek;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.BudulecSwiata;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

public final class ObslugaMenu
{
    private static final char WYBOR_PLIK = 'p';
    private static final char WYBOR_LOSOWO = 'l';

    private Swiat swiat;
    private BudulecSwiata budulec;

    private void obslozInicjalizacjeSwiata()
    {
        char wybor = '\0';
        while (wybor != WYBOR_PLIK && wybor != WYBOR_LOSOWO)
        {
            //cout<<"Wczytac swiat z pliku (podaj "<<WYBOR_PLIK<<") czy wygenerowac losowy? (podaj "<<WYBOR_LOSOWO<<")"<<endl;
            //cin>>wybor;
        }
        if (wybor == WYBOR_PLIK)
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
        //system("cls");
        //cout<<"Symulowanie..."<<endl;
        swiat.WykonajTure();
        swiat.RysujSwiat();
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
        String nazwaPliku = "";
        //cout<<"Podaj nazwe pliku"<<endl;
        //cin>>nazwaPliku;
        try
        {
            budulec.WczytajZPliku(swiat, nazwaPliku);
        }
        catch (Exception e)
        {
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
        char wybor = '\0';
        //cout<<"Czy zapisac do pliku? (t/N)"<<endl;
        //cin>>wybor;
        if (wybor == 't')
        {
            String nazwaPliku = "";
            //cout<<"Podaj nazwe pliku"<<endl;
            //cin>>nazwaPliku;
            budulec.ZapiszDoPliku(swiat, nazwaPliku);
        }
    }

    public void Wykonaj()
    {
        char wybor = '\0';

        obslozInicjalizacjeSwiata();
        swiat.RysujSwiat();
        while (true)
        {
            obslozTure();
            //cout<<"Czy przejsc do nastepnej tury? (T/n)"<<endl;
            //cin>>wybor;
            if (wybor == 'n')
                break;
        }
    }
};