package business.dto;

public class CastellerDTO extends PeriodeDTO {
	private final String dni;
	private final String nom;
	private final String cognom1;
	private final String cognom2;
	private final String sexe;

	public CastellerDTO(String dni, String nom, String cognom1, String cognom2, String sexe, String dataNaixement, String dataDefuncio) {
		super(dataNaixement, dataDefuncio);
		this.dni = dni;
		this.nom = nom;
		this.cognom1 = cognom1;
		this.cognom2 = cognom2;
		this.sexe = sexe;
	}

	public String getDni() {
		return dni;
	}

	public String getNom() {
		return nom;
	}

	public String getCognom1() {
		return cognom1;
	}

	public String getCognom2() {
		if (cognom2.isEmpty()) return null;
		return cognom2;
	}

	public String getSexe() {
		return sexe;
	}

	public String getDataNaixement() {
		return super.getDesDe();
	}

	public String getDataDefuncio() {
		return super.getFinsA();
	}
}
