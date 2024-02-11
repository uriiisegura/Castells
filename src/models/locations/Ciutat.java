package models.locations;

import java.util.Vector;

public class Ciutat {
	private final String nom;
	private final Pais pais;
	private final Vector<Location> locations = new Vector<>();

	public Ciutat(String nom, Pais pais) {
		this.nom = nom;
		this.pais = pais;
	}

	public void addLocation(Location location) {
		locations.add(location);
	}

	public String getNom() {
		return nom;
	}

	public Pais getPais() {
		return pais;
	}
}
