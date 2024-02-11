package models.locations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pais {
	private final String nom;
	private final List<Ciutat> ciutats = new ArrayList<>();

	public Pais(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void addCiutat(Ciutat ciutat) {
		ciutats.add(ciutat);
	}

	public HashMap<String, Ciutat> getCiutats() {
		HashMap<String, Ciutat> ciutatsMap = new HashMap<>();
		for (Ciutat ciutat : ciutats) {
			ciutatsMap.put(ciutat.getNom(), ciutat);
		}
		return ciutatsMap;
	}
}
