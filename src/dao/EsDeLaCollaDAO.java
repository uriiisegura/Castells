package dao;

import models.castellers.Casteller;
import models.colles.Colla;

import java.util.List;

public interface EsDeLaCollaDAO {
	void loadAll(List<Casteller> castellers, List<Colla> colles);
}
