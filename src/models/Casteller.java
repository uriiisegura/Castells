package models;

import relationships.EsDeLaColla;
import relationships.Periode;

import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;

public class Casteller extends Periode {
	private final String dni;
	private final String nom;
	private final String cognom1;
	private final String cognom2;
	private final Vector<EsDeLaColla> colles = new Vector<>();

	public Casteller(String dni, String nom, String cognom1, String cognom2, LocalDate dataNaixement, LocalDate dataDefuncio) {
		super(dataNaixement, dataDefuncio);
		this.dni = dni;
		this.nom = nom;
		this.cognom1 = cognom1;
		this.cognom2 = cognom2;
	}

	public boolean addColla(EsDeLaColla colla) {
		return colles.add(colla);
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
