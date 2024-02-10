package persistence.dao;

import models.castells.Estructura;

import java.util.List;

public interface EstructuraDAO {
	List<Estructura> loadAll();
}
