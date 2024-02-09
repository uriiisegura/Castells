package relationships;

import models.Periode;

import java.time.LocalDate;

public class CollaNom extends Periode {
	private final String nom;

	public CollaNom(String nom, LocalDate dataInici, LocalDate dataFi) {
		super(dataInici, dataFi);
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}
}
