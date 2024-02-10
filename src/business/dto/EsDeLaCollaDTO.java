package business.dto;

public class EsDeLaCollaDTO {
	private final String desDe;
	private final String finsA;
	private final String malnom;

	public EsDeLaCollaDTO(String desDe, String finsA, String malnom) {
		this.desDe = desDe;
		this.finsA = finsA;
		this.malnom = malnom;
	}

	public String getDesDe() {
		return desDe;
	}

	public String getFinsA() {
		if (finsA.isEmpty()) return null;
		return finsA;
	}

	public String getMalnom() {
		return malnom;
	}
}
