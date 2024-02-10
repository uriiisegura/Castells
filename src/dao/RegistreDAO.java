package dao;

import models.castellers.Casteller;

import java.util.List;

public interface RegistreDAO {
	void loadAll(List<Casteller> castellers);
}
