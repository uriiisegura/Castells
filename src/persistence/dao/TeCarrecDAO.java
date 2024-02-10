package persistence.dao;

import models.castellers.Casteller;
import models.colles.Carrec;
import models.colles.Colla;

import java.util.List;

public interface TeCarrecDAO {
	void loadAll(List<Casteller> castellers, List<Colla> colles, List<Carrec> carrecs);
}
