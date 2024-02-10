package models.diades;

import models.castellers.Casteller;
import relationships.CastellDiada;

public class PomLineUp extends CastellLineUp {
	private Casteller dosos1;
	private Casteller dosos2;
	private Casteller acotxador;
	private Casteller enxaneta;

	public PomLineUp(CastellDiada castell, Casteller dosos1, Casteller dosos2, Casteller acotxador, Casteller enxaneta) {
		super(castell);
		this.dosos1 = dosos1;
		this.dosos2 = dosos2;
		this.acotxador = acotxador;
		this.enxaneta = enxaneta;
	}
}
