package models;

import java.util.ArrayList;
import java.util.List;

public class Placa extends Location {
	private String id;
	private String name;
	private Ciutat ciutat;
	private List<Diada> diades = new ArrayList<>();

	public Placa(String id, String name, Ciutat ciutat, double longitude, double latitude) {
		super(longitude, latitude);
		this.id = id;
		this.name = name;
		this.ciutat = ciutat;
	}

	public boolean addDiada(Diada diada) {
		return diades.add(diada);
	}

	public String getId() {
		return id;
	}
}
