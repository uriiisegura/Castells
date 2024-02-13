package models.relationships;

import models.Periode;
import models.castells.Castell;

import java.time.LocalDate;

public class EstaPuntuat extends Periode {
	private final Castell castell;
	private int carregat;
	private int descarregat;
	private int grup;
	private int subgrup;

	public EstaPuntuat(Castell castell, LocalDate desDe, LocalDate finsA, int carregat, int descarregat, int grup, int subgrup) {
		super(desDe, finsA);
		this.castell = castell;
		this.carregat = carregat;
		this.descarregat = descarregat;
		this.grup = grup;
		this.subgrup = subgrup;
	}
}
