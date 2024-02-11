package models.castells;

import java.util.ArrayList;
import java.util.List;

public class Estructura {
	private final String notacio;
	private final String nom;
	private final List<Rengla> rengles = new ArrayList<>();
	private final List<Castell> castells = new ArrayList<>();

	public Estructura(String notacio, String nom) {
		this.notacio = notacio;
		this.nom = nom;
	}

	public void addRengla(Rengla rengla) {
		rengles.add(rengla);
	}

	public void addCastell(Castell castell) {
		castells.add(castell);
	}

	public String getNotacio() {
		return notacio;
	}

	public String getNom() {
		return nom;
	}
}
