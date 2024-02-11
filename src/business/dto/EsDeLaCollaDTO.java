package business.dto;

public class EsDeLaCollaDTO extends PeriodeDTO {
	private final String malnom;

	public EsDeLaCollaDTO(String desDe, String finsA, String malnom) {
		super(desDe, finsA);
		this.malnom = malnom;
	}

	public String getMalnom() {
		return malnom;
	}
}
