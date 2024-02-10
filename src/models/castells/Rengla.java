package models.castells;

public class Rengla {
	private final Estructura estructura;
	private final String nom;

	public Rengla(Estructura estructura, String nom) {
		this.estructura = estructura;
		this.nom = nom;
	}

	public Estructura getEstructura() {
		return estructura;
	}

	public String getNom() {
		return nom;
	}
}
