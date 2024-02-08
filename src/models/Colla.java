package models;


import relationships.*;

import java.util.Vector;

public abstract class Colla {
	private final String id;
	private final Vector<EsDeLaColla> castellers = new Vector<>();
	private Vector<TeCarrec> carrecs = new Vector<>();
	private Vector<CollaFundacio> fundacions = new Vector<>();
	private Vector<CollaNom> noms = new Vector<>();
	private Vector<CollaColor> colors = new Vector<>();
	private Vector<CollaAdreca> adreces = new Vector<>();

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

	public String getId() {
		return id;
	}
}
