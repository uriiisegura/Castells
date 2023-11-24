package models;

import java.util.ArrayList;
import java.util.List;

public class Ciutat {
	private String id;
	private String nom;
	private List<Placa> places = new ArrayList<>();

	public Ciutat(String id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	public boolean addPlaca(Placa placa) {
		return places.add(placa);
	}

	public String getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}
}
