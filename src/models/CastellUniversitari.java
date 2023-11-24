package models;

import enums.EstructuresT;
import enums.PisosT;
import enums.ReforcosT;

public class CastellUniversitari extends Castell {
    private boolean margarita;

    public CastellUniversitari(EstructuresT estructura, PisosT pisos, ReforcosT reforcos, int agulles, boolean perSota, int enxanetes, boolean margarita) {
        super(estructura, pisos, reforcos, agulles, perSota, enxanetes);
        this.margarita = margarita;
    }

    @Override
    public String toString() {
        return String.format("%sd%d%s%s%s%s", getEstructura().string(), getPisos().toInt(), getReforcos().string(),
                "a".repeat(getAgulles()), margarita ? "m" : "", isPerSota() ? "s" : "");
    }
}
