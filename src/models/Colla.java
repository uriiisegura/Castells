package models;

import config.Config;
import exceptions.HasNoAddressException;
import exceptions.HasNoNameException;
import relationships.*;

import java.time.LocalDate;
import java.util.Vector;

public abstract class Colla {
	private String id;
	private Vector<CollaNom> noms = new Vector<>();
	private Vector<CollaFundacio> fundacions = new Vector<>();
	private Vector<CollaColor> colors = new Vector<>();
	private Vector<CollaAdreca> adreces = new Vector<>();
	private Vector<EsDeLaColla> castellers = new Vector<>();
	private Vector<TeCarrec> carrecs = new Vector<>();
	private Vector<CastellDiada> castells = new Vector<>();

	public Colla(String id) {
		this.id = id;
	}

	public boolean addNom(CollaNom nom) {
		return noms.add(nom);
	}

	public boolean addFundacio(CollaFundacio fundacio) {
		return fundacions.add(fundacio);
	}

	public boolean addColor(CollaColor color) {
		return colors.add(color);
	}

	public boolean addAdreca(CollaAdreca adreca) {
		return adreces.add(adreca);
	}

	public boolean addCasteller(EsDeLaColla casteller) {
		return castellers.add(casteller);
	}

	public boolean addCarrec(TeCarrec carrec) {
		return carrecs.add(carrec);
	}

	public boolean addCastell(CastellDiada castell) {
		return castells.add(castell);
	}

	public String getId() {
		return id;
	}

	public String getName(LocalDate date) throws HasNoNameException {
		for (CollaNom nom : noms)
			if (nom.isActive(date)) return nom.getNom();
		throw new HasNoNameException(String.format("The colla has no active name for the given date (%s).",
				Config.parseDate(date)));
	}

	public String getName() throws HasNoNameException {
		return getName(LocalDate.now());
	}

	public String getAddress(LocalDate date) throws HasNoAddressException {
		for (CollaAdreca adreca : adreces)
			if (adreca.isActive(date)) return adreca.getAdreca();
		throw new HasNoAddressException(String.format("The colla has no active address for the given date (%s).",
				Config.parseDate(date)));
	}

	public String getAddress() throws HasNoAddressException {
		return getAddress(LocalDate.now());
	}
}
