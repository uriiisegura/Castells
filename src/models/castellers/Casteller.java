package models.castellers;


import exceptions.ValuelessAtDateException;
import exceptions.ValuelessEverException;
import models.Periode;
import models.colles.Colla;
import models.diades.CastellLineUp;
import relationships.EsDeLaColla;
import relationships.TeCarrec;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Casteller extends Periode {
	private final String dni;
	private String nom;
	private String cognom1;
	private String cognom2;
	private String sexe;
	private final List<EsDeLaColla> colles = new ArrayList<>();
	private final List<TeCarrec> carrecs = new ArrayList<>();
	private final List<Registre> registres = new ArrayList<>();
	private final List<CastellLineUp> castells = new ArrayList<>();

	public Casteller(String dni, String nom, String cognom1, String cognom2, String sexe, LocalDate dataNaixement, LocalDate dataDefuncio) {
		super(dataNaixement, dataDefuncio);
		this.dni = dni;
		this.nom = nom;
		this.cognom1 = cognom1;
		this.cognom2 = cognom2;
		this.sexe = sexe;
	}

	public void addColla(EsDeLaColla colla) {
		colles.add(colla);
	}

	public void addCarrec(TeCarrec carrec) {
		carrecs.add(carrec);
	}

	public void addRegistre(Registre registre) {
		registres.add(registre);
	}

	public void addCastell(CastellLineUp castell) {
		castells.add(castell);
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
		return cognom2;
	}

	public String getSexe() {
		return sexe;
	}

	public LocalDate getDataNaixement() {
		return super.getDesDe();
	}

	public LocalDate getDataDefuncio() {
		return super.getFinsA();
	}

	public String getFullName() {
		if (cognom2 != null)
			return String.format("%s %s i %s", nom, cognom1, cognom2);
		return String.format("%s %s", nom, cognom1);
	}

	public String getMalnomInCollaAt(Colla colla, LocalDate date) throws ValuelessAtDateException {
		for (EsDeLaColla e : colles) {
			if (e.getColla().equals(colla) && e.isActive(date))
				return e.getMalnom();
		}
		throw new ValuelessAtDateException("El casteller no té malnom a la colla " + colla.getNomAt(date) + " a la data " + date + ".");
	}

	public List<EsDeLaColla> getPeriodsInColla(Colla colla) throws ValuelessEverException {
		List<EsDeLaColla> periods = new ArrayList<>();
		for (EsDeLaColla e : colles) {
			if (e.getColla().equals(colla))
				periods.add(e);
		}
		if (periods.isEmpty())
			throw new ValuelessEverException("El casteller mai ha estat a aquesta colla.");
		return periods;
	}
}
