package models.diades;

import models.colles.Colla;
import models.locations.Location;
import models.relationships.CastellDiada;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Diada {
	private final String id;
	private String nom;
	private ZonedDateTime inici;
	private ZonedDateTime fi;
	private Location location;
	private final List<CastellDiada> castells = new ArrayList<>();

	public Diada(String id, String nom, ZonedDateTime inici, ZonedDateTime fi, Location location) {
		this.id = id;
		this.nom = nom;
		this.inici = inici;
		this.fi = fi;
		this.location = location;
	}

	public void addCastell(CastellDiada castell) {
		castells.add(castell);
	}

	public String getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public ZonedDateTime getInici() {
		return inici;
	}

	public List<CastellDiada> getCastells() {
		return castells;
	}

	public HashMap<Colla, List<CastellDiada>> getCastellsDictionary() {
		HashMap<Colla, List<CastellDiada>> castellsDictionary = new HashMap<>();
		for (CastellDiada c : castells) {
			if (!castellsDictionary.containsKey(c.getColla()))
				castellsDictionary.put(c.getColla(), new ArrayList<>());
			castellsDictionary.get(c.getColla()).add(c);
		}
		return castellsDictionary;
	}

	public String getFullLocation() {
		return location.getFullLocation();
	}
}
