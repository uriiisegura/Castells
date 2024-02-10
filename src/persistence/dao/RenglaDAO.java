package persistence.dao;

import models.castells.Estructura;
import models.castells.Rengla;

import java.util.List;

public interface RenglaDAO {
	List<Rengla> loadAll(List<Estructura> estructures);
}
