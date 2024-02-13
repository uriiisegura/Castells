package models.colles;

import exceptions.ValuelessAtDateException;
import exceptions.ValuelessEverException;
import models.Periode;
import models.relationships.CastellDiada;
import models.relationships.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Colla {
	private final String id;
	private final List<EsDeLaColla> castellers = new ArrayList<>();
	private final List<TeCarrec> carrecs = new ArrayList<>();
	private final List<CollaFundacio> fundacions = new ArrayList<>();
	private final List<CollaNom> noms = new ArrayList<>();
	private final List<CollaColor> colors = new ArrayList<>();
	private final List<CollaAdreca> adreces = new ArrayList<>();
	private final List<CastellDiada> castells = new ArrayList<>();

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

	public List<CollaFundacio> getFundacions() throws ValuelessEverException {
		if (fundacions.isEmpty())
			throw new ValuelessEverException("La colla mai ha tingut cap fundació.");
		return fundacions;
	}

	public List<CollaNom> getNoms() throws ValuelessEverException {
		if (noms.isEmpty())
			throw new ValuelessEverException("La colla mai ha tingut cap nom.");
		return noms;
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

	public List<CollaColor> getColors() throws ValuelessEverException {
		if (colors.isEmpty())
			throw new ValuelessEverException("La colla mai ha tingut cap color.");
		return colors;
	}

	public List<CollaAdreca> getAdreces() throws ValuelessEverException {
		if (adreces.isEmpty())
			throw new ValuelessEverException("La colla mai ha tingut cap adreça.");
		return adreces;
	}
}
