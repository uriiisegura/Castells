package models.diades;

import relationships.CastellDiada;

public abstract class CastellLineUp {
	private CastellDiada castell;

	public CastellLineUp(CastellDiada castell) {
		this.castell = castell;
	}
}