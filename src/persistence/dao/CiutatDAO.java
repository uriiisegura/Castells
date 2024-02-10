package persistence.dao;

import models.locations.Ciutat;

import java.util.List;

public interface CiutatDAO {
	List<Ciutat> loadAll();
}
