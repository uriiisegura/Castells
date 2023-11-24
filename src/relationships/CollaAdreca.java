package relationships;

import java.time.LocalDate;

public class CollaAdreca extends Periode {
	private final String adreca;

	public CollaAdreca(String adreca, LocalDate desDe, LocalDate finsA) {
		super(desDe, finsA);
		this.adreca = adreca;
	}

	public String getAdreca() {
		return adreca;
	}
}
