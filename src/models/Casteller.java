package models;


import relationships.EsDeLaColla;
import relationships.TeCarrec;

import java.time.LocalDate;
import java.util.Vector;

public class Casteller extends Periode {
	private final String dni;
	private String nom;
	private String cognom1;
	private String cognom2;
	private Vector<EsDeLaColla> colles = new Vector<>();
	private Vector<TeCarrec> carrecs = new Vector<>();

	public Casteller(String dni, String nom, String cognom1, String cognom2, LocalDate dataNaixement, LocalDate dataDefuncio) {
		super(dataNaixement, dataDefuncio);
		this.dni = dni;
		this.nom = nom;
		this.cognom1 = cognom1;
		this.cognom2 = cognom2;
	}

	public void addColla(EsDeLaColla colla) {
		colles.add(colla);
	}

	public void addCarrec(TeCarrec carrec) {
		carrecs.add(carrec);
	}

	public String getDni() {
		return dni;
	}

	public String getFullName() {
		if (cognom2 != null)
			return String.format("%s %s i %s", nom, cognom1, cognom2);
		return String.format("%s %s", nom, cognom1);
	}
}
