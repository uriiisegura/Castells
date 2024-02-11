package models.locations;

public abstract class Location {
	private final String id;
	private String nom;
	private Ciutat ciutat;

	public Location(String id, String nom, Ciutat ciutat) {
		this.id = id;
		this.nom = nom;
		this.ciutat = ciutat;
	}

	public String getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getFullLocation() {
		return nom + ", " + ciutat.getNom() + ", " + ciutat.getPais().getNom();
	}
}
