package business.dto;

public class CollaAdrecaDTO extends PeriodeDTO {
	private final String adreca;

	public CollaAdrecaDTO(String adreca, String dataInici, String dataFi) {
		super(dataInici, dataFi);
		this.adreca = adreca;
	}

	public String getAdreca() {
		return adreca;
	}
}
