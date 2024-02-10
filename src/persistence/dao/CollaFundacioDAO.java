package persistence.dao;

import models.colles.Colla;

import java.util.List;

public interface CollaFundacioDAO {
	void loadAll(List<Colla> colles);
}
