package pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Kierunek;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.ObslugaRuchu;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public final class Czlowiek extends Zwierze
{
    public static final char IDENTYFIKATOR_PLIKU = 'C';

    private static final int ILOSC_TUR_DO_ODTWORZENIA_SUPERMOCY = 5;
    private static final int ILOSC_TUR_DO_OSLABIENIA_SUPERMOCY = 3;
    private static final int ILOSC_TUR_Z_WAZNA_SUPERMOCA = 5;
    private static final int KROK_Z_SUPERMOCA = 2;
    private static final int PROCENT_SZANS_NA_SUPERMOC_PO_OSLABIENIU = 50;
    private static final int STANDARDOWY_KROK = 1;

    private int pozostalaIloscTurZSupermoca;
    private int iloscTurDoUzyciaSupermocy;
    private int wybranaZmianaX, wybranaZmianaY;

    @Override
    protected Czlowiek zwrocInstancjeZwierzecia(int x, int y)
    {
        return new Czlowiek(x, y);
    }

    private void aktualizujStanSupermocyPoUzyciu()
    {
        if (GetPozostalaIloscTurZSupermoca() == 1)
            iloscTurDoUzyciaSupermocy = ILOSC_TUR_DO_ODTWORZENIA_SUPERMOCY;

        pozostalaIloscTurZSupermoca--;
    }

    private boolean czyDzialaSupermoc()
    {
        Random losowanie = new Random();
        if (pozostalaIloscTurZSupermoca > ILOSC_TUR_Z_WAZNA_SUPERMOCA - ILOSC_TUR_DO_OSLABIENIA_SUPERMOCY)
            return true;
        else if (pozostalaIloscTurZSupermoca > 0 && losowanie.nextInt(100) < PROCENT_SZANS_NA_SUPERMOC_PO_OSLABIENIU)
            return true;

        return false;
    }


    public Czlowiek(int x, int y)
    {
        super(x, y);
        kolorPola = Color.MAGENTA;
        nazwa = "Czlowiek";
        inicjatywa = 4;
        sila = 5;
        znakASCII = IDENTYFIKATOR_PLIKU;

        iloscTurDoUzyciaSupermocy = 0;
        pozostalaIloscTurZSupermoca = 0;
    }

    @Override
    public void Akcja(Swiat swiat)
    {
        previousX = x;
        previousY = y;
        SprubojPrzesunacO(wybranaZmianaX, wybranaZmianaY, swiat);

        if (GetPozostalaIloscTurZSupermoca() == 0)
        {
            if (GetIloscTurDoUzyciaSupermocy() > 0)
                iloscTurDoUzyciaSupermocy--;

            return;
        }

        aktualizujStanSupermocyPoUzyciu();
    }

    @Override
    public void Kolizja(Swiat swiat, Organizm organizm)
    {
    }

    public int GetIloscTurDoUzyciaSupermocy()
    {
        return iloscTurDoUzyciaSupermocy;
    }

    public int GetPozostalaIloscTurZSupermoca()
    {
        return pozostalaIloscTurZSupermoca;
    }

    public void SetIloscTurDoUzyciaSupermocy(int iloscTurDoUzyciaSupermocy)
    {
        this.iloscTurDoUzyciaSupermocy = iloscTurDoUzyciaSupermocy;
    }

    public void SetPozostalaIloscTurZSupermoca(int pozostalaIloscTurZSupermoca)
    {
        this.pozostalaIloscTurZSupermoca = pozostalaIloscTurZSupermoca;
    }

    public void WczytajInformacjeORuchu(Swiat swiat)
    {
        ObslugaRuchu obslugaRuchu = new ObslugaRuchu(swiat, this);
        Kierunek kierunek = obslugaRuchu.CzekajNaWybor();
        if (kierunek == Kierunek.LEWO)
            SprubojPrzesunacO(-getDlugoscKroku(), 0, swiat);
        else if (kierunek == Kierunek.PRAWO)
            SprubojPrzesunacO(getDlugoscKroku(), 0, swiat);
        else if (kierunek == Kierunek.GORA)
            SprubojPrzesunacO(0, -getDlugoscKroku(), swiat);
        else
            SprubojPrzesunacO(0, getDlugoscKroku(), swiat);
    }

    public void SprobujAktywowacSuperMoc()
    {
        if (GetIloscTurDoUzyciaSupermocy() > 0)
            return;

        pozostalaIloscTurZSupermoca = ILOSC_TUR_Z_WAZNA_SUPERMOCA;
        iloscTurDoUzyciaSupermocy = ILOSC_TUR_DO_ODTWORZENIA_SUPERMOCY;
    }

    private int getDlugoscKroku()
    {
        if (pozostalaIloscTurZSupermoca > 0)
            return KROK_Z_SUPERMOCA;
        else
            return DOMYSLNY_KROK;
    }
};