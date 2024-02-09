package models.castells;

public class Pisos {
	private final String notacio;
	private final String nom;

	public Pisos(String notacio, String nom) {
		this.notacio = notacio;
		this.nom = nom;
	}

	public String getNotacio() {
		return notacio;
	}

	public String getNom() {
		return nom;
	}
}
