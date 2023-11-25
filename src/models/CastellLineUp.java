package models;

import enums.RenglesT;
import relationships.CastellDiada;

public abstract class CastellLineUp {
	private CastellDiada castell;
	private RenglesT rengla;

	public CastellLineUp(CastellDiada castell, RenglesT rengla) {
		this.castell = castell;
		this.rengla = rengla;
	}

	public CastellDiada getCastell() {
		return castell;
	}

	public RenglesT getRengla() {
		return rengla;
	}

	abstract public boolean isInLineUp(Casteller c);

	abstract public boolean isInLineUp(String dni);
}
