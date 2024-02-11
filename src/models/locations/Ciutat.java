package models.locations;

import java.util.ArrayList;
import java.util.List;

public class Ciutat {
	private final String nom;
	private final Pais pais;
	private final List<Location> locations = new ArrayList<>();

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
