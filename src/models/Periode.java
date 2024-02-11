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

	public boolean overlaps(Periode periode) {
		if (periode.getFinsA() == null) {
			if (finsA == null)
				return true;
			return finsA.isAfter(periode.getDesDe());
		}
		if (finsA == null)
			return desDe.isBefore(periode.getFinsA()) && desDe.isAfter(periode.getDesDe());
		return (desDe.isBefore(periode.getFinsA()) && desDe.isAfter(periode.getDesDe())) || (finsA.isBefore(periode.getFinsA()) && finsA.isAfter(periode.getDesDe()));
	}
}
