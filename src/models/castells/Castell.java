package models.castells;

import models.relationships.CastellDiada;
import models.relationships.EstaPuntuat;

import java.util.ArrayList;
import java.util.List;

public abstract class Castell {
	private final String id;
	private final Estructura estructura;
	private final Pisos pisos;
	private final Reforcos reforcos;
	private final int agulles;
	private final boolean perSota;
	private final boolean caminant;
	private final int enxanetes;
	private List<CastellDiada> fets = new ArrayList<>();
	private List<EstaPuntuat> puntuacions = new ArrayList<>();

	public Castell(String id, Estructura estructura, Pisos pisos, Reforcos reforcos, int agulles, boolean perSota, boolean caminant, int enxanetes) {
		this.id = id;
		this.estructura = estructura;
		this.pisos = pisos;
		this.reforcos = reforcos;
		this.agulles = agulles;
		this.perSota = perSota;
		this.caminant = caminant;
		this.enxanetes = enxanetes;
	}

	public void addFet(CastellDiada castell) {
		fets.add(castell);
	}

	public void addPuntuacio(EstaPuntuat puntuacio) {
		puntuacions.add(puntuacio);
	}

	public String getId() {
		return id;
	}

	public Estructura getEstructura() {
		return estructura;
	}

	public String getNotacio() {
		return String.format("%sd%s%s%s%s%s",
				estructura.getNotacio(),
				pisos.getNotacio(),
				reforcos.getNotacio(),
				new String(new char[agulles]).replace("\0", "a"),
				perSota ? "ps" : "",
				caminant ? "cam" : ""
		);
	}

	public String getNom() {
		String nexeAgulla = reforcos.getNom().isEmpty() ? "amb" : "i";
		return String.format("%s de %s %s %s %s %s",
				estructura.getNom(),
				pisos.getNom(),
				reforcos.getNom(),
				agulles > 1 ? nexeAgulla + " " + agulles + " agulles" : (agulles == 1 ? nexeAgulla + " agulla" : ""),
				perSota ? "per sota" : "",
				caminant ? "caminant" : ""
		).trim().replaceAll(" +", " ");
	}
}
