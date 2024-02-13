package models.diades;

import models.relationships.CastellDiada;

public abstract class CastellLineUp {
	private CastellDiada castell;

	public CastellLineUp(CastellDiada castell) {
		this.castell = castell;
	}

	public abstract boolean hasCasteller(String dni);
}
