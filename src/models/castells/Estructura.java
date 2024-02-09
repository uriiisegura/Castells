package models.castells;

import java.util.Vector;

public class Estructura {
	private final String notacio;
	private final String nom;
	private final Vector<Rengla> rengles = new Vector<>();
	private final Vector<Castell> castells = new Vector<>();

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
