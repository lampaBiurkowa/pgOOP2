package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.Czlowiek;

import java.util.ArrayList;

public class Swiat
{
    private Organizm[] organizmy;
    private int iloscOrganizmow;
    private ArrayList<String> komunikaty;
    private int maxInicjatywa;
    private int numerTury;
    private int szerokosc, wysokosc;

    private int getMaxInicjatywa(int ograniczenieGorneWlaczne)
    {
        int max = -1;
        for (int i = 0; i < iloscOrganizmow; i++)
            if (organizmy[i] != null && organizmy[i].GetInicjatywa() <= ograniczenieGorneWlaczne && organizmy[i].GetInicjatywa() > max)
                max = organizmy[i].GetInicjatywa();

        return max;
    }
    
    private void rysujKomunikaty()
    {
        //cout<<"Komunikaty:"<<endl;
        if (komunikaty.size() == 0)
        {
            //cout<<"Brak"<<endl;
            return;
        }

        //for (int i = 0; i < komunikaty.size(); i++)
            //cout<<komunikaty[i]<<endl;

        komunikaty.clear();
    }
    
    private void rysujMape()
    {

    }
    
    private void obsluzEwentualneKolizje(Organizm organizmZOstatniaAkcja)
    {
        Kolizja kolizja = new Kolizja();
        Organizm organizmAtakowany, organizmAtakujacy;
        for (int i = 0; i < GetWysokosc(); i++)
            for (int j = 0; j < GetSzerokosc(); j++)
            {
                if (!CzyPoleZajete(j, i))
                    continue;

                kolizja.SzukajKolizji(this, j, i);
                if (!kolizja.WystepujeKolizja())
                    continue;

                if (kolizja.GetKolidujaceOrganizmy()[0] == organizmZOstatniaAkcja)
                {
                    organizmAtakowany = kolizja.GetKolidujaceOrganizmy()[1];
                    organizmAtakujacy = kolizja.GetKolidujaceOrganizmy()[0];
                }
                else
                {
                    organizmAtakowany = kolizja.GetKolidujaceOrganizmy()[0];
                    organizmAtakujacy = kolizja.GetKolidujaceOrganizmy()[1];
                }

                DodajKomunikat("Kolizja " + organizmAtakowany.GetNazwa() + " z " + organizmAtakujacy.GetNazwa());
                organizmAtakowany.Kolizja(this, organizmAtakujacy);
                kolizja.SzukajKolizji(this, j, i);
                if (!kolizja.WystepujeKolizja())
                    continue;

                if (organizmAtakowany.GetSila() >= organizmAtakujacy.GetSila())
                {
                    DodajKomunikat(organizmAtakowany.GetNazwa() + " zabija " + organizmAtakujacy.GetNazwa());
                    UsunOrganizm(organizmAtakujacy);
                }
                else
                {
                    DodajKomunikat(organizmAtakujacy.GetNazwa() + " zabija " + organizmAtakowany.GetNazwa());
                    UsunOrganizm(organizmAtakowany);
                }
            }
    }

    private void ustawOrganizmyWKolejnosciRuchow(Organizm[] ustawioneOrganizmy)
    {
        int ograniczenieGorneWlaczne = maxInicjatywa;
        int iloscUstawionychOrganizmow = 0;
        while (iloscUstawionychOrganizmow < iloscOrganizmow)
        {
            int szukanaInicjatywa = getMaxInicjatywa(ograniczenieGorneWlaczne);
            ograniczenieGorneWlaczne = szukanaInicjatywa - 1;
            for (int i = 0; i < iloscOrganizmow; i++)
                if (organizmy[i].GetInicjatywa() == szukanaInicjatywa)
                    {
                        ustawioneOrganizmy[iloscUstawionychOrganizmow] = organizmy[i];
                        iloscUstawionychOrganizmow++;
                    }
        }
    }

    private void zaktualizujTabliceOrganizmow()
    {
        int wolnyIndeks = GetSzerokosc() * GetWysokosc();
        boolean czySzukacWolnegoIndeksu = true;
        for (int i = 0; i < GetSzerokosc() * GetWysokosc(); i++)
        {
            if (czySzukacWolnegoIndeksu && organizmy[i] == null)
            {
                wolnyIndeks = i;
                czySzukacWolnegoIndeksu = false;
            }
            else if (organizmy[i] != null && i > wolnyIndeks)
            {
                int roznica = i - wolnyIndeks;
                for (int j = wolnyIndeks; j < GetSzerokosc() * GetWysokosc() - roznica; j++)
                    organizmy[j] = organizmy[j + roznica];

                i = wolnyIndeks;
                wolnyIndeks = GetSzerokosc() * GetWysokosc();
                czySzukacWolnegoIndeksu = true;
            }
        }

        for (int i = iloscOrganizmow; i < GetSzerokosc() * GetWysokosc(); i++)
            organizmy[i] = null;
    }

    public boolean CzyPoleZajete(int x, int y)
    {
        for (int i = 0; i < wysokosc * szerokosc; i++)
            if (organizmy[i] != null && organizmy[i].GetX() == x && organizmy[i].GetY() == y)
                return true;

        return false;
    }
    
    public boolean CzyPunktMiesciSieNaMapie(int x, int y)
    {
        return x < szerokosc && y < wysokosc && x >= 0 && y >= 0;
    }
    
    public boolean CzyOrganizmJestNaPolu(int x, int y, String nazwa)
    {
        for (int i = 0; i < GetWysokosc() * GetSzerokosc(); i++)
            if (organizmy[i] != null  && organizmy[i].GetX() == x && organizmy[i].GetY() == y && organizmy[i].GetNazwa().equals(nazwa))
        return true;

        return false;
    }
    
    public void DodajKomunikat(String tresc)
    {
        komunikaty.add(tresc);
    }

    public void DodajOrganizm(Organizm organizm)
    {
        zaktualizujTabliceOrganizmow();
        for (int i = 0; i < wysokosc * szerokosc; i++)
            if (organizmy[i] == null)
            {
                organizmy[i] = organizm;
                iloscOrganizmow++;
                return;
            }
    }

    public int GetIloscOrganizmow()
    {
        return iloscOrganizmow;
    }

    public Organizm GetOrganizmNaPozycji(int x, int y)
    {
        for (int i = 0; i < GetWysokosc() * GetSzerokosc(); i++)
            if (organizmy[i] != null && organizmy[i].GetX() == x && organizmy[i].GetY() == y)
                return organizmy[i];

        return null;
    }

    public Organizm[] GetOrganizmy()
    {
        return organizmy;
    }

    public int GetNumerTury()
    {
        return numerTury;
    }

    public int GetSzerokosc()
    {
        return szerokosc;
    }

    public int GetWysokosc()
    {
        return  wysokosc;
    }

    public void RysujSwiat()
    {

    }

    public Czlowiek SprobujZnalezcCzlowieka()
    {
        for (int i = 0; i < GetWysokosc(); i++)
            for (int j = 0; j < GetSzerokosc(); j++)
                if (CzyPoleZajete(j, i) && GetOrganizmNaPozycji(j, i) instanceof Czlowiek)
                    return (Czlowiek)GetOrganizmNaPozycji(j, i);

        return null;
    }

    public void Stworz(int szerokosc, int wysokosc, int numerTury)
    {
        iloscOrganizmow = 0;
        maxInicjatywa = 7;
        this.numerTury = numerTury;
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        organizmy = new Organizm[wysokosc * szerokosc];
        for (int i = 0; i < wysokosc * szerokosc; i++)
            organizmy[i] = null;
    }

    public void UsunOrganizm(Organizm organizm)
    {
        if (organizm == null)
            return;

        for (int i = 0; i < wysokosc * szerokosc; i++)
            if (organizmy[i] == organizm)
            {
                organizmy[i] = null;
                iloscOrganizmow--;
            }
    }

    public void WykonajTure()
    {
        Organizm[] ustawioneOrganizmy = new Organizm[iloscOrganizmow];
        ustawOrganizmyWKolejnosciRuchow(ustawioneOrganizmy);

        int iloscUstawionychOrganizmow = iloscOrganizmow;
        for (int i = 0; i < iloscUstawionychOrganizmow; i++) {
            if (ustawioneOrganizmy[i] == null)
                continue;

            ustawioneOrganizmy[i].Akcja(this);
            obsluzEwentualneKolizje(ustawioneOrganizmy[i]);
        }

        ustawioneOrganizmy = null;
        zaktualizujTabliceOrganizmow();
        numerTury++;
    }

    public void WypelnijSasiadujacePola(int[][] tablicaDocelowa, int zasieg, int x, int y)
    {
        int iterator = 0;
        for (int i = -zasieg; i <= zasieg; i++)
            for (int j = -zasieg; j <= zasieg; j++)
            {
                if ((i == 0 && j == 0) || (i != 0 && j != 0))
                    continue;

                tablicaDocelowa[iterator][0] = x + j;
                tablicaDocelowa[iterator][1] = y + i;
            }
    }
}
