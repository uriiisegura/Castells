package models;

import enums.EstructuresT;
import enums.PisosT;
import enums.ReforcosT;
import relationships.CastellDiada;
import relationships.EstaPuntuat;

import java.util.Vector;

public class Castell {
    private EstructuresT estructura;
    private PisosT pisos;
    private ReforcosT reforcos;
    private int agulles;
    private boolean perSota;
    private int enxanetes;
    private Vector<EstaPuntuat> puntuacions = new Vector<>();
    private Vector<CastellDiada> realitzacions = new Vector<>();

    public Castell(EstructuresT estructura, PisosT pisos, ReforcosT reforcos, int agulles, boolean perSota, int enxanetes) {
        this.estructura = estructura;
        this.pisos = pisos;
        this.reforcos = reforcos;
        this.agulles = agulles;
        this.perSota = perSota;
        this.enxanetes = enxanetes;
    }

    public boolean addPuntuacio(EstaPuntuat puntuacio) {
        return puntuacions.add(puntuacio);
    }

    public boolean addRealitzacio(CastellDiada realitzacio) {
        return realitzacions.add(realitzacio);
    }

    public EstructuresT getEstructura() {
        return estructura;
    }

    public PisosT getPisos() {
        return pisos;
    }

    public ReforcosT getReforcos() {
        return reforcos;
    }

    public int getAgulles() {
        return agulles;
    }

    public boolean isPerSota() {
        return perSota;
    }

    public int calcularPisosRengla() {
        if (estructura.equals(EstructuresT.PILAR))
            return pisos.toInt();
        return pisos.toInt() - 3;
    }

    @Override
    public String toString() {
        return String.format("%sd%d%s%s%s", getEstructura().string(), getPisos().toInt(), getReforcos().string(),
                "a".repeat(getAgulles()), isPerSota() ? "s" : "");
    }
}
