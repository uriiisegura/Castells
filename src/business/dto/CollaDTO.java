package business.dto;

public class CollaDTO {
	private final String id;
	private final boolean esUniversitaria;

	public CollaDTO(String id, boolean esUniversitaria) {
		this.id = id;
		this.esUniversitaria = esUniversitaria;
	}

	public String getId() {
		return id;
	}

	public boolean esUniversitaria() {
		return esUniversitaria;
	}
}
