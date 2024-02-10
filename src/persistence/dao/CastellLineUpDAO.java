package persistence.dao;

import models.castellers.Casteller;
import models.castells.Rengla;
import relationships.CastellDiada;

import java.util.List;

public interface CastellLineUpDAO {
	void loadAll(List<Casteller> castellers, List<CastellDiada> castells, List<Rengla> rengles);
}
