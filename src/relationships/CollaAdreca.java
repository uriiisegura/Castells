package relationships;

import models.Ciutat;
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
}
