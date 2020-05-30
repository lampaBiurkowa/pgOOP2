package pl.edu.pg.eti.ksg.po.projekt2.organizmy.zwierzeta;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

import java.util.Random;

public abstract class Zwierze extends Organizm
{
        static final int ZASIEG_USTAWIENIA_POTOMKA = 1;
        static final int DOMYSLNY_KROK = 1;

        protected int previousX, previousY;
        protected int zwiekszenieSily;

        protected boolean czyMaGdzieUstawicPotomka(Swiat swiat)
        {
            for (int i = -ZASIEG_USTAWIENIA_POTOMKA; i <= ZASIEG_USTAWIENIA_POTOMKA; i++)
                for (int j = -ZASIEG_USTAWIENIA_POTOMKA; j <= ZASIEG_USTAWIENIA_POTOMKA; j++)
                {
                    if ((i == 0 && j == 0) || (i != 0 && j != 0) || x + j == previousX && y + i == previousY || !swiat.CzyPunktMiesciSieNaMapie(x + j, y + i))
                        continue;

                    if (!swiat.CzyPoleZajete(x + j, y + i) || !swiat.CzyOrganizmJestNaPolu(x + j, y + i, nazwa))
                        return true;
                }

            return false;
        }
        protected boolean sprobujDodacPotomka(Swiat swiat)
        {
            Random losowanie = new Random();
            int zmianaX = losowanie.nextInt(2 * ZASIEG_USTAWIENIA_POTOMKA + 1) - ZASIEG_USTAWIENIA_POTOMKA;
            int zmianaY = losowanie.nextInt(2 * ZASIEG_USTAWIENIA_POTOMKA + 1) - ZASIEG_USTAWIENIA_POTOMKA;
            if ((zmianaX == 0 && zmianaY == 0) || (zmianaX != 0 && zmianaY != 0) || !swiat.CzyPunktMiesciSieNaMapie(x + zmianaX, y + zmianaY))
                return false;

            if (x + zmianaX == previousX && y + zmianaY == previousY)
                return false;

            if (swiat.CzyPoleZajete(x + zmianaX, y + zmianaY) && swiat.CzyOrganizmJestNaPolu(x + zmianaX, y + zmianaY, nazwa))
                return false;

            Zwierze zwierze = zwrocInstancjeZwierzecia(x + zmianaX, y + zmianaY);
            swiat.DodajOrganizm(zwierze);
            return true;
        }

        protected boolean sprubojWykonacRuch(Swiat swiat, int krok)
        {
            Random losowanie = new Random();
            int zmianaX = losowanie.nextInt(2 * krok + 1) - krok;
            int zmianaY = losowanie.nextInt(2 * krok + 1) - krok;

            if ((zmianaX == 0 && zmianaY == 0) || (zmianaX != 0 && zmianaY != 0))
                return false;

            return SprubojPrzesunacO(zmianaX, zmianaY, swiat);
        }

        protected abstract Zwierze zwrocInstancjeZwierzecia(int x, int y);

        public Zwierze(int x, int y)
        {
            super(x, y);
            previousX = x;
            previousY = y;
            zwiekszenieSily = 0;
        }

        public void Cofnij()
        {
            x = previousX;
            y = previousY;
        }

        public int GetZwiekszenieSily()
        {
            return zwiekszenieSily;
        }

        public void OznaczZwiekszenieSily(int ilosc)
        {
            zwiekszenieSily += ilosc;
        }

        public boolean SprubojPrzesunacO(int x, int y, Swiat swiat)
        {
            if (!swiat.CzyPunktMiesciSieNaMapie(this.x + x, this.y + y))
                return false;

            this.x += x;
            this.y += y;
            return true;
        }

        @Override
        public void Akcja(Swiat swiat)
        {
            previousX = x;
            previousY = y;
            boolean czyUdaloSiePoruszyc = false;
            while (!czyUdaloSiePoruszyc)
                czyUdaloSiePoruszyc = sprubojWykonacRuch(swiat, DOMYSLNY_KROK);
        }

        @Override
        public void Kolizja(Swiat swiat, Organizm organizm)
        {
            if (organizm.GetNazwa().equals(nazwa))
            {
                Cofnij();
                if (!czyMaGdzieUstawicPotomka(swiat))
                    return;
                boolean czyUdaloSieRomznozyc = false;
                while (!czyUdaloSieRomznozyc)
                    czyUdaloSieRomznozyc = sprobujDodacPotomka(swiat);

                swiat.DodajKomunikat("rodzi sie " + nazwa);
            }
        }
        };