package business.dto;

public class PeriodeDTO {
	private final String desDe;
	private final String finsA;

	public PeriodeDTO(String desDe, String finsA) {
		this.desDe = desDe;
		this.finsA = finsA;
	}

	public String getDesDe() {
		return desDe;
	}

	public String getFinsA() {
		if (finsA.isEmpty()) return null;
		return finsA;
	}
}
