package models.relationships;

import models.locations.Ciutat;
import models.Periode;

import java.time.LocalDate;

public class CollaAdreca extends Periode {
	private String adreca;
	private Ciutat ciutat;

	public CollaAdreca(String adreca, Ciutat ciutat, LocalDate desDe, LocalDate finsA) {
		super(desDe, finsA);
		this.adreca = adreca;
		this.ciutat = ciutat;
	}

	public String getAdreca() {
		return String.format("%s, %s", adreca, ciutat.getNom());
	}

	public String getAdrecaCompleta() {
		return String.format("%s, %s, %s", adreca, ciutat.getNom(), ciutat.getPais().getNom());
	}

	public String getCiutatNom() {
		return ciutat.getNom();
	}

	public String getPaisNom() {
		return ciutat.getPais().getNom();
	}
}
