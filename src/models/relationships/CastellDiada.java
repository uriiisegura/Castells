package models.relationships;

import enums.ResultatsT;
import models.castells.Castell;
import models.castells.Estructura;
import models.colles.Colla;
import models.diades.CastellLineUp;
import models.diades.Diada;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CastellDiada {
	private final long id;
	private Diada diada;
	private Castell castell;
	private ResultatsT resultat;
	private Colla colla;
	private int ordre;
	private final List<CastellLineUp> lineUps = new ArrayList<>();

	public CastellDiada(long id, Diada diada, Castell castell, ResultatsT resultat, Colla colla, int ordre) {
		this.id = id;
		this.diada = diada;
		this.castell = castell;
		this.resultat = resultat;
		this.colla = colla;
		this.ordre = ordre;
	}

	public void addLineUp(CastellLineUp lineUp) {
		lineUps.add(lineUp);
	}

	public long getId() {
		return id;
	}

	public String getNotacio() {
		return castell.getNotacio();
	}

	public String getNom() {
		return castell.getNom();
	}

	public Estructura getEstructura() {
		return castell.getEstructura();
	}

	public ResultatsT getResultat() {
		return resultat;
	}

	public Colla getColla() {
		return colla;
	}

	public List<CastellLineUp> getLineUps() {
		return lineUps;
	}

	public boolean hasCasteller(String dni) {
		for (CastellLineUp lineUp : lineUps) {
			if (lineUp.hasCasteller(dni))
				return true;
		}
		return false;
	}

	public String getDiadaNom() {
		return diada.getNom();
	}

	public LocalDate getDate() {
		return diada.getInici().toLocalDate();
	}

	public String getFullLocation() {
		return diada.getFullLocation();
	}
}
