package relationships;

import java.time.LocalDate;

public class Periode {
	private final LocalDate desDe;
	private final LocalDate finsA;

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
