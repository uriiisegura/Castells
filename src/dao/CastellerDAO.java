package dao;

import models.castellers.Casteller;

import java.util.List;

public interface CastellerDAO {
	List<Casteller> loadAll();
}
