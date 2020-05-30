package pl.edu.pg.eti.ksg.po.projekt2.organizmy;

import pl.edu.pg.eti.ksg.po.projekt2.swiat.Swiat;

public abstract class Organizm
{
    protected String nazwa;
    protected int inicjatywa;
    protected int sila;
    protected int x, y;
    protected char znakASCII;
    public Organizm(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int GetInicjatywa()
    {
        return inicjatywa;
    }

    public String GetNazwa()
    {
        return nazwa;
    }

    public int GetSila()
    {
        return sila;
    }

    public int GetX()
    {
        return x;
    }

    public int GetY()
    {
        return y;
    }

    public char GetZnakASCII()
    {
        return znakASCII;
    }

    public void SetSila(int sila)
    {
        this.sila = sila;
    }

    public abstract void Akcja(Swiat swiat);
    public abstract void Kolizja(Swiat swiat, Organizm organizm);

    public void Rysuj()
    {

    }
};