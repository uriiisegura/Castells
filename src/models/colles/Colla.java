package models.colles;


import exceptions.ValuelessAtDateException;
import exceptions.ValuelessEverException;
import models.Periode;
import relationships.CastellDiada;
import relationships.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Vector;

public abstract class Colla {
	private final String id;
	private final Vector<EsDeLaColla> castellers = new Vector<>();
	private Vector<TeCarrec> carrecs = new Vector<>();
	private Vector<CollaFundacio> fundacions = new Vector<>();
	private Vector<CollaNom> noms = new Vector<>();
	private Vector<CollaColor> colors = new Vector<>();
	private Vector<CollaAdreca> adreces = new Vector<>();
	private Vector<CastellDiada> castells = new Vector<>();

	public Colla(String id) {
		this.id = id;
	}

	public void addCasteller(EsDeLaColla casteller) {
		castellers.add(casteller);
	}

	public void addCarrec(TeCarrec carrec) {
		carrecs.add(carrec);
	}

	public void addFundacio(CollaFundacio fundacio) {
		fundacions.add(fundacio);
	}

	public void addNom(CollaNom nom) {
		noms.add(nom);
	}

	public void addColor(CollaColor color) {
		colors.add(color);
	}

	public void addAdreca(CollaAdreca adreca) {
		adreces.add(adreca);
	}

	public void addCastell(CastellDiada castell) {
		castells.add(castell);
	}

	public String getId() {
		return id;
	}

	public String getNomAt(LocalDate data) throws ValuelessAtDateException {
		for (CollaNom nom : noms) {
			if (nom.isActive(data))
				return nom.getNom();
		}
		throw new ValuelessAtDateException("No hi ha cap nom actiu per a la data " + data + ".");
	}

	public String getCurrentNom() throws ValuelessAtDateException{
		return getNomAt(LocalDate.now());
	}

	public String getLastName() throws ValuelessEverException {
		if (noms.isEmpty())
			throw new ValuelessEverException("La colla mai ha tingut cap nom.");
		return noms.stream().max(Comparator.comparing(Periode::getFinsA)).get().getNom();
	}
}
