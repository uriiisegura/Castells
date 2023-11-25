package models;

import enums.RenglesT;
import relationships.CastellDiada;

public class PomLineUp extends CastellLineUp {
	private Casteller dosos1;
	private Casteller dosos2;
	private Casteller acotxador;
	private Casteller enxaneta;

	public PomLineUp(CastellDiada castell, RenglesT rengla, Casteller dosos1, Casteller dosos2, Casteller acotxador, Casteller enxaneta) {
		super(castell, rengla);
		this.dosos1 = dosos1;
		this.dosos2 = dosos2;
		this.acotxador = acotxador;
		this.enxaneta = enxaneta;
	}

	public boolean isInLineUp(Casteller c) {
		return dosos1.equals(c) || dosos2.equals(c) || acotxador.equals(c) || enxaneta.equals(c);
	}

	public boolean isInLineUp(String dni) {
		return dosos1.getDni().equals(dni) || dosos2.getDni().equals(dni) || acotxador.getDni().equals(dni) || enxaneta.getDni().equals(dni);
	}
}
