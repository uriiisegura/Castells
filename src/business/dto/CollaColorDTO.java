package business.dto;

public class CollaColorDTO extends PeriodeDTO {
	private final String color;

	public CollaColorDTO(String desDe, String finsA, String color) {
		super(desDe, finsA);
		this.color = color;
	}

	public String getColor() {
		return color;
	}
}
