package relationships;

import enums.ResultatsT;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class CastellDiada {
	private long id;
	private Colla colla;
	private Diada diada;
	private Castell castell;
	private ResultatsT resultat;
	private int ordre;
	private List<CastellLineUp> lineUp = new ArrayList<>();

	public CastellDiada(long id, Colla colla, Diada diada, Castell castell, ResultatsT resultat, int ordre) {
		this.id = id;
		this.colla = colla;
		this.diada = diada;
		this.castell = castell;
		this.resultat = resultat;
		this.ordre = ordre;
	}

	public boolean addLineUp(CastellLineUp lineUp) {
		return this.lineUp.add(lineUp);
	}

	public long getId() {
		return id;
	}

	public Castell getCastell() {
		return castell;
	}

	public List<CastellLineUp> getLineUp() {
		return lineUp;
	}

	public boolean isInLineUp(Casteller c) {
		for (CastellLineUp lu : lineUp)
			if (lu.isInLineUp(c)) return true;
		return false;
	}

	public boolean isInLineUp(String dni) {
		for (CastellLineUp lu : lineUp)
			if (lu.isInLineUp(dni)) return true;
		return false;
	}
}
