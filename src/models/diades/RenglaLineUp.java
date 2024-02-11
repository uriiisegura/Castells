package models.diades;

import models.castellers.Casteller;
import models.castells.Rengla;
import models.colles.Colla;
import relationships.CastellDiada;

import java.time.LocalDate;
import java.util.List;

public class RenglaLineUp extends CastellLineUp {
	private Rengla rengla;
	private List<Casteller> castellers;

	public RenglaLineUp(CastellDiada castell, Rengla rengla, List<Casteller> castellers) {
		super(castell);
		this.rengla = rengla;
		this.castellers = castellers;
	}

	public String getRenglaNom() {
		return rengla.getNom();
	}

	public String[] getMalnomsAt(Colla colla, LocalDate date) {
		String[] malnoms = new String[castellers.size()];
		for (int i = 0; i < castellers.size(); i++)
			malnoms[i] = castellers.get(i).getMalnomInCollaAt(colla, date);
		return malnoms;
	}

	@Override
	public boolean hasCasteller(String dni) {
		return castellers.stream().anyMatch(c -> c.getDni().equals(dni));
	}
}
