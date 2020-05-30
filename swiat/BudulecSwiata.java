package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.rosliny.*;
import pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta.*;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public final class BudulecSwiata
{
    private static final int ILOSC_ARGUMENOW_METADANYCH = 3, ILOSC_ARGUMENOW_SUPERMOCY = 2, ILOSC_ARGUMENOW_SILY = 3;
    private static final int ILOSC_GATUNKOW_DO_LOSOWANIA = 10;
    private static final int X_INDEKS = 0, Y_INDEKS = 1;
    private static final int NUMER_TURY_INDEKS = 2;
    private static final int POZOSTALE_TURY_Z_SUPERMOCA_INDEKS = 0, ILOSC_TUR_DO_AKTYWACJI_SUPERMOCY_INDEKS = 1;
    private static final int ZWIEKSZENIE_SILY_INDEKS = 2;
    private static final char SEPARATOR_W_PLIKU = ',', PUSTE_POLE = '-', OZNACZENIE_SUPERMOCY = 'S';

    private void dodajOdpowiedniTypOrganizmu(Swiat swiat, char znak, int x, int y)
    {
        if (znak == Antylopa.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new Antylopa(x, y));
        else if (znak == BarszczSosnowskiego.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new BarszczSosnowskiego(x, y));
        else if (znak == Czlowiek.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new Czlowiek(x, y));
        else if (znak == Guarana.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new Guarana(x, y));
        else if (znak == Lis.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new Lis(x, y));
        else if (znak == Mlecz.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new Mlecz(x, y));
        else if (znak == Owca.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new Owca(x, y));
        else if (znak == Trawa.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new Trawa(x, y));
        else if (znak == WilczeJagody.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new WilczeJagody(x, y));
        else if (znak == Wilk.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new Wilk(x, y));
        else if (znak == Zolw.IDENTYFIKATOR_PLIKU)
            swiat.DodajOrganizm(new Zolw(x, y));
    }

    private void obsluzPotencjalnyBladWczytywania(int oczekiwanaIloscArg, int aktualnaIloscArg)
    {
        if (aktualnaIloscArg != oczekiwanaIloscArg)
        {
            //cout<<"Blad! Ilosc argumentow w metadnych: "<<aktualnaIloscArg<<", oczekiwano: "<<oczekiwanaIloscArg<<endl;
            System.exit(-1);
        }
    }

    private boolean pozycjaZajeta(int x, int y, int[][] pozycje, int iloscSztuk)
    {
        for (int i = 0; i < iloscSztuk * ILOSC_GATUNKOW_DO_LOSOWANIA + 1; i++)
            if (pozycje[i][0] == x && pozycje[i][1] == y)
                return true;

        return false;
    }

    private String przygotujMetadaneDoZapisu(Swiat swiat)
    {
        String metadane = Integer.toString(swiat.GetSzerokosc()) + SEPARATOR_W_PLIKU;
        metadane += Integer.toString(swiat.GetWysokosc()) + SEPARATOR_W_PLIKU;
        metadane += Integer.toString(swiat.GetNumerTury());

        return metadane;
    }

    private int[][] przygotujPozycjeStartowe(Swiat swiat, int iloscSztuk)
    {
        int[][] pozycje = new int[iloscSztuk * ILOSC_GATUNKOW_DO_LOSOWANIA + 1][];
        for (int i = 0; i < iloscSztuk * ILOSC_GATUNKOW_DO_LOSOWANIA + 1; i++)
            pozycje[i] = new int[2];

        Random losowanie = new Random();
        for (int i = 0; i < iloscSztuk * ILOSC_GATUNKOW_DO_LOSOWANIA + 1; i++)
        {
            boolean udaloSieDodac = false;
            while (!udaloSieDodac)
            {
                int x = losowanie.nextInt(swiat.GetSzerokosc());
                int y = losowanie.nextInt(swiat.GetWysokosc());
                if (!pozycjaZajeta(x, y, pozycje, iloscSztuk))
                {
                    udaloSieDodac = true;
                    pozycje[i][0] = x;
                    pozycje[i][1] = y;
                }
            }
        }

        return pozycje;
    }

    private void sprobujWdrozycInformacjeOSileZPliku(Swiat swiat, String zrodlo)
    {
        int[] dane = new int[ILOSC_ARGUMENOW_SILY];
        int iloscParametrow = wypelnijLiczbowaTabliceZPlikuIZwrocIlosc(zrodlo, dane, 0);
        obsluzPotencjalnyBladWczytywania(ILOSC_ARGUMENOW_SILY, iloscParametrow);

        int x = dane[X_INDEKS], y = dane[Y_INDEKS];
        int zwiekszenieSily = dane[ZWIEKSZENIE_SILY_INDEKS];
        Organizm organizm = swiat.GetOrganizmNaPozycji(x, y);
        if (swiat.CzyPoleZajete(x, y) && organizm instanceof Zwierze)
        {
            ((Zwierze)organizm).OznaczZwiekszenieSily(zwiekszenieSily);
            organizm.SetSila(organizm.GetSila() + zwiekszenieSily);
        }
    }

    private void sprobujWdrozycInformacjeOSupermocyZPliku(Swiat swiat, String zrodlo)
    {
        Czlowiek czlowiek = swiat.SprobujZnalezcCzlowieka();
        if (czlowiek == null)
            return;

        int[] dane = new int[ILOSC_ARGUMENOW_SUPERMOCY];
        int iloscParametrow = wypelnijLiczbowaTabliceZPlikuIZwrocIlosc(zrodlo, dane, 1);
        obsluzPotencjalnyBladWczytywania(ILOSC_ARGUMENOW_SUPERMOCY, iloscParametrow);

        czlowiek.SetPozostalaIloscTurZSupermoca(dane[POZOSTALE_TURY_Z_SUPERMOCA_INDEKS]);
        czlowiek.SetIloscTurDoUzyciaSupermocy(dane[ILOSC_TUR_DO_AKTYWACJI_SUPERMOCY_INDEKS]);
    }

    private String sprobujZapisacInformacjeOSupermocy(Organizm organizm)
    {
        String informacjeOSupermocy = "";
        if (organizm instanceof Czlowiek)
        {
            int pozostalaIloscTurZSupemoca = ((Czlowiek)(organizm)).GetPozostalaIloscTurZSupermoca();
            int iloscTurDoUzyciaSupermocy = ((Czlowiek)(organizm)).GetIloscTurDoUzyciaSupermocy();
            informacjeOSupermocy = OZNACZENIE_SUPERMOCY + Integer.toString(pozostalaIloscTurZSupemoca) + SEPARATOR_W_PLIKU + Integer.toString(iloscTurDoUzyciaSupermocy);
        }

        return informacjeOSupermocy;
    }

    private void sprobujZapisacInformacjeOZwiekszeniuSily(Organizm organizm, ArrayList<String> informacjeOSile)
    {
        if (organizm instanceof Zwierze)
        {
            int zwiekszenieSily = ((Zwierze)(organizm)).GetZwiekszenieSily();
            if (((Zwierze)(organizm)).GetZwiekszenieSily() != 0)
            informacjeOSile.add(Integer.toString(organizm.GetX()) + SEPARATOR_W_PLIKU + Integer.toString(organizm.GetY()) + SEPARATOR_W_PLIKU + Integer.toString(zwiekszenieSily));
        }
    }

    private void wdrozMetadane(Swiat swiat, String zrodlo)
    {
        int[] dane = new int[ILOSC_ARGUMENOW_METADANYCH];
        int iloscParametrow = wypelnijLiczbowaTabliceZPlikuIZwrocIlosc(zrodlo, dane, 0);
        obsluzPotencjalnyBladWczytywania(ILOSC_ARGUMENOW_METADANYCH, iloscParametrow);
        swiat.Stworz(dane[X_INDEKS], dane[Y_INDEKS], dane[NUMER_TURY_INDEKS]);
    }

    private void wdrozPozostaleInformacjeZPliku(Swiat swiat, String zrodlo)
    {
        if (zrodlo.length() == 0)
            return;

        if (zrodlo.charAt(0) == OZNACZENIE_SUPERMOCY)
            sprobujWdrozycInformacjeOSupermocyZPliku(swiat, zrodlo);
        else
            sprobujWdrozycInformacjeOSileZPliku(swiat, zrodlo);
    }

    private void wykonajZapisDanych(String sciezka, String metadane, ArrayList<String> linieMapy, ArrayList<String> informacjeOSile, String informacjeOSupermocy) throws IOException
    {
        FileWriter zapis = new FileWriter(sciezka);
        zapis.write(metadane);
        for (String s : linieMapy) zapis.append(s);
        for (String s : informacjeOSile) zapis.append(s);
        zapis.append(informacjeOSupermocy);
        zapis.close();
    }

    private int wypelnijLiczbowaTabliceZPlikuIZwrocIlosc(String zrodlo, int[] tablicaDocelowa, int startIteracji)
    {
        String numer = "";
        int iterator = 0;
        for (int i = startIteracji; i < zrodlo.length(); i++)
        {
            if (zrodlo.charAt(i) != SEPARATOR_W_PLIKU)
                numer += zrodlo.charAt(i);
            if (zrodlo.charAt(i) == SEPARATOR_W_PLIKU || i == zrodlo.length() - 1)
            {
                tablicaDocelowa[iterator++] = Integer.parseInt(numer);
                numer = "";
            }
        }

        return iterator;
    }

    public void RozstawOrganizmyLosowo(Swiat swiat, int iloscSztuk)
    {
        if (iloscSztuk * ILOSC_GATUNKOW_DO_LOSOWANIA + 1 > swiat.GetSzerokosc() * swiat.GetWysokosc())
        {
            //cout<<"Zbyt maly rozmiar swiat do zmieszczeia wszystkich gatunkow";
            return;
        }

        int[][] pozycje = przygotujPozycjeStartowe(swiat, iloscSztuk);
        for (int i = 0; i < iloscSztuk * ILOSC_GATUNKOW_DO_LOSOWANIA; i++)
        {
            swiat.DodajOrganizm(new Antylopa(pozycje[i][0], pozycje[i][1])); i++;
            swiat.DodajOrganizm(new BarszczSosnowskiego(pozycje[i][0], pozycje[i][1])); i++;
            swiat.DodajOrganizm(new Guarana(pozycje[i][0], pozycje[i][1])); i++;
            swiat.DodajOrganizm(new Lis(pozycje[i][0], pozycje[i][1])); i++;
            swiat.DodajOrganizm(new Mlecz(pozycje[i][0], pozycje[i][1])); i++;
            swiat.DodajOrganizm(new Owca(pozycje[i][0], pozycje[i][1])); i++;
            swiat.DodajOrganizm(new Trawa(pozycje[i][0], pozycje[i][1])); i++;
            swiat.DodajOrganizm(new WilczeJagody(pozycje[i][0], pozycje[i][1])); i++;
            swiat.DodajOrganizm(new Wilk(pozycje[i][0], pozycje[i][1])); i++;
            swiat.DodajOrganizm(new Zolw(pozycje[i][0], pozycje[i][1]));
        }
        swiat.DodajOrganizm(new Czlowiek(pozycje[iloscSztuk * ILOSC_GATUNKOW_DO_LOSOWANIA][0], pozycje[iloscSztuk * ILOSC_GATUNKOW_DO_LOSOWANIA][1]));
    }

    public void WczytajZPliku(Swiat swiat, String sciezka) throws FileNotFoundException
    {
        String linia;
        File dane = new File(sciezka);
        Scanner skaner = new Scanner(dane);
        linia = skaner.nextLine();
        wdrozMetadane(swiat, linia);
        for (int i = 0; i < swiat.GetWysokosc(); i++)
        {
            linia = skaner.nextLine();
            for (int j = 0; j < linia.length(); j += 2)
                dodajOdpowiedniTypOrganizmu(swiat, linia.charAt(j), j / 2, i);
        }

        while (!skaner.hasNextLine())
        {
            linia = skaner.nextLine();
            wdrozPozostaleInformacjeZPliku(swiat, linia);
        }
        skaner.close();
    }

    public void ZapiszDoPliku(Swiat swiat, String sciezka)
    {
        ArrayList<String> linieMapy = new ArrayList<>();
        ArrayList<String> informacjOSile = new ArrayList<>();
        String informacjeOSupermocy = "";
        for (int i = 0; i < swiat.GetWysokosc(); i++)
        {
            String linia = "";
            for (int j = 0; j < swiat.GetSzerokosc(); j++)
            {
                if (!swiat.CzyPoleZajete(j, i))
                    linia += PUSTE_POLE;
                else
                {
                    Organizm organizm = swiat.GetOrganizmNaPozycji(j, i);
                    linia += organizm.GetZnakASCII();
                    sprobujZapisacInformacjeOZwiekszeniuSily(organizm, informacjOSile);
                    String wynikProby = sprobujZapisacInformacjeOSupermocy(organizm);
                    if (wynikProby.length() > 0)
                        informacjeOSupermocy = wynikProby;
                }

                if (j != swiat.GetSzerokosc() - 1)
                    linia += SEPARATOR_W_PLIKU;
            }
            linieMapy.add(linia);
        }

        try
        {
            wykonajZapisDanych(sciezka, przygotujMetadaneDoZapisu(swiat), linieMapy, informacjOSile, informacjeOSupermocy);
        }
        catch (Exception e)
        {
        }
    }
};