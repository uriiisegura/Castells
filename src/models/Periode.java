package models;

import java.time.LocalDate;

public class Periode {
	private LocalDate desDe;
	private LocalDate finsA;

	public Periode(LocalDate desDe, LocalDate finsA) {
		this.desDe = desDe;
		this.finsA = finsA;
	}

	public LocalDate getDesDe() {
		return desDe;
	}

	public LocalDate getFinsA() {
		return finsA;
	}

	public boolean isActive(LocalDate date) {
		if (finsA == null)
			return date.isAfter(desDe);
		return date.isAfter(desDe) && date.isBefore(finsA);
	}
}
