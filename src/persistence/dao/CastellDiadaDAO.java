package persistence.dao;

import models.castells.Castell;
import models.colles.Colla;
import models.diades.Diada;
import relationships.CastellDiada;

import java.util.List;

public interface CastellDiadaDAO {
	List<CastellDiada> loadAll(List<Diada> diades, List<Castell> castells, List<Colla> colles);
}
