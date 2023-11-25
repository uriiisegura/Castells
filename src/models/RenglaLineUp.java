package models;

import enums.RenglesT;
import relationships.CastellDiada;

public class RenglaLineUp extends CastellLineUp {
	private Casteller[] lineUp;

	public RenglaLineUp(CastellDiada castell, RenglesT rengla) {
		super(castell, rengla);
		this.lineUp = new Casteller[castell.getCastell().calcularPisosRengla()];
	}

	public boolean addCasteller(int index, Casteller casteller) {
		if (lineUp[index] != null) return false;
		lineUp[index] = casteller;
		return true;
	}

	public Casteller[] getLineUp() {
		return lineUp;
	}

	public boolean isInLineUp(Casteller c) {
		for (Casteller casteller : lineUp)
			if (casteller.equals(c)) return true;
		return false;
	}

	public boolean isInLineUp(String dni) {
		for (Casteller casteller : lineUp)
			if (casteller.getDni().equals(dni)) return true;
		return false;
	}
}
