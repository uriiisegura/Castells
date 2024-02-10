package models.diades;

import models.castellers.Casteller;
import models.colles.Colla;
import models.locations.Location;
import relationships.CastellDiada;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Diada {
	private final String id;
	private String nom;
	private ZonedDateTime inici;
	private ZonedDateTime fi;
	private Location location;
	private Vector<CastellDiada> castells = new Vector<>();

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

	public Vector<CastellDiada> getCastells() {
		return castells;
	}

	public HashMap<Colla, Vector<CastellDiada>> getCastellsDictionary() {
		HashMap<Colla, Vector<CastellDiada>> castellsDictionary = new HashMap<>();
		for (CastellDiada c : castells) {
			if (!castellsDictionary.containsKey(c.getColla()))
				castellsDictionary.put(c.getColla(), new Vector<>());
			castellsDictionary.get(c.getColla()).add(c);
		}
		return castellsDictionary;
	}
}
