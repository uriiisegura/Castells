package models.colles;

public abstract class Carrec {
	private String masculi;
	private String femeni;
	private String neutre;

	public Carrec(String masculi, String femeni, String neutre) {
		this.masculi = masculi;
		this.femeni = femeni;
		this.neutre = neutre;
	}

	public String getMasculi() {
		return masculi;
	}
}
