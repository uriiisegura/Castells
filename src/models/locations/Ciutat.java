package models.locations;

import java.util.Vector;

public class Ciutat {
	private final String id;
	private final String nom;
	private final Vector<Location> locations = new Vector<>();

	public Ciutat(String id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	public void addLocation(Location location) {
		locations.add(location);
	}

	public String getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}
}
