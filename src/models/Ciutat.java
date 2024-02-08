package models;

public class Ciutat {
	private final String id;
	private final String nom;

	public Ciutat(String id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	public String getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}
}
