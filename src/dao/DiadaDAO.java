package dao;

import models.diades.Diada;
import models.locations.Location;

import java.util.List;

public interface DiadaDAO {
	List<Diada> loadAll(List<Location> locations);
}
