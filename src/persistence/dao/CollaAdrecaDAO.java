package persistence.dao;

import models.colles.Colla;
import models.locations.Ciutat;

import java.util.List;

public interface CollaAdrecaDAO {
	void loadAll(List<Colla> colles, List<Ciutat> ciutats);
}
