package relationships;

import enums.ResultatsT;
import models.Castell;
import models.Colla;
import models.Diada;

public class CastellDiada {
	private long id;
	private Colla colla;
	private Diada diada;
	private Castell castell;
	private ResultatsT resultat;
	private int ordre;

	public CastellDiada(long id, Colla colla, Diada diada, Castell castell, ResultatsT resultat, int ordre) {
		this.id = id;
		this.colla = colla;
		this.diada = diada;
		this.castell = castell;
		this.resultat = resultat;
		this.ordre = ordre;
	}

	public long getId() {
		return id;
	}
}
