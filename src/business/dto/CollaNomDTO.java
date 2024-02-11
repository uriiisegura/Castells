package business.dto;

public class CollaNomDTO extends PeriodeDTO {
	private final String nom;

	public CollaNomDTO(String nom, String dataInici, String dataFi) {
		super(dataInici, dataFi);
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}
}
