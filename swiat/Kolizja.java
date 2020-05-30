package pl.edu.pg.eti.ksg.po.projekt2.swiat;

import pl.edu.pg.eti.ksg.po.projekt2.organizmy.Organizm;
import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

class Kolizja
{
    private static final int MAX_ORGANIZMOW_KOLIDUJACYCH = 2;

    private int iloscKolidujacychOrganizmow;
    private Organizm[] kolidujaceOrganizmy;

    private void inicjuj()
    {
        for (int i = 0; i < MAX_ORGANIZMOW_KOLIDUJACYCH; i++)
            kolidujaceOrganizmy[i] = null;

        iloscKolidujacychOrganizmow = 0;
    }

    public Kolizja()
    {
        kolidujaceOrganizmy = new Organizm[]{null, null};
        inicjuj();
    }

    public Organizm[] GetKolidujaceOrganizmy()
    {
        return kolidujaceOrganizmy;
    }

    public void SzukajKolizji(Swiat swiat, int x, int y)
    {
        inicjuj();
        for (int i = 0; i < swiat.GetWysokosc() * swiat.GetSzerokosc(); i++)
            if (swiat.GetOrganizmy()[i] != null && swiat.GetOrganizmy()[i].GetX() == x && swiat.GetOrganizmy()[i].GetY() == y)
        {
            kolidujaceOrganizmy[iloscKolidujacychOrganizmow] = swiat.GetOrganizmy()[i];
            iloscKolidujacychOrganizmow++;
        }
    }

    public boolean WystepujeKolizja()
    {
        return iloscKolidujacychOrganizmow == MAX_ORGANIZMOW_KOLIDUJACYCH;
    }
};