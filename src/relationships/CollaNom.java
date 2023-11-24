package relationships;

import models.Periode;

import java.time.LocalDate;

public class CollaNom extends Periode {
	private String nom;

	public CollaNom(String nom, LocalDate desDe, LocalDate finsA) {
		super(desDe, finsA);
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}
}
