package models;

import enums.EstructuresT;
import enums.PisosT;
import enums.ReforcosT;
import relationships.EstaPuntuat;

import java.util.Vector;

public class Castell {
    private final EstructuresT estructura;
    private final PisosT pisos;
    private final ReforcosT reforcos;
    private final int agulles;
    private final boolean perSota;
    private final int enxanetes;
    private final Vector<EstaPuntuat> puntuacions = new Vector<>();

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

    @Override
    public String toString() {
        return String.format("%sd%d%s%s%s", getEstructura().string(), getPisos().toInt(), getReforcos().string(),
                "a".repeat(getAgulles()), isPerSota() ? "s" : "");
    }
}
