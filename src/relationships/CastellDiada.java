package relationships;

import enums.ResultatsT;
import models.castells.Castell;
import models.colles.Colla;
import models.diades.Diada;

public class CastellDiada {
	private final long id;
	private Diada diada;
	private Castell castell;
	private ResultatsT resultat;
	private Colla colla;
	private int ordre;

	public CastellDiada(long id, Diada diada, Castell castell, ResultatsT resultat, Colla colla, int ordre) {
		this.id = id;
		this.diada = diada;
		this.castell = castell;
		this.resultat = resultat;
		this.colla = colla;
		this.ordre = ordre;
	}

	public String getNotacio() {
		return castell.getNotacio();
	}

	public String getNom() {
		return castell.getNom();
	}

	public ResultatsT getResultat() {
		return resultat;
	}

	public Colla getColla() {
		return colla;
	}
}
