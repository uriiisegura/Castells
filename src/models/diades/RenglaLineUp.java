package models.diades;

import models.castellers.Casteller;
import models.castells.Rengla;
import relationships.CastellDiada;

import java.util.Vector;

public class RenglaLineUp extends CastellLineUp {
	private Rengla rengla;
	private Vector<Casteller> castellers;

	public RenglaLineUp(CastellDiada castell, Rengla rengla, Vector<Casteller> castellers) {
		super(castell);
		this.rengla = rengla;
		this.castellers = castellers;
	}

	public String getRenglaNom() {
		return rengla.getNom();
	}

	public Vector<Casteller> getCastellers() {
		return castellers;
	}
}
