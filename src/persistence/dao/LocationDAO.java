package persistence.dao;

import models.locations.Ciutat;
import models.locations.Location;

import java.util.List;

public interface LocationDAO {
	List<Location> loadAll(List<Ciutat> ciutats);
}
