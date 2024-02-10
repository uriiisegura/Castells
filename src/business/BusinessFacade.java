package business;

import exceptions.WrongCredentialsException;
import persistence.dao.*;
import persistence.SqlConnection;
import models.castellers.Casteller;
import models.castells.*;
import models.colles.Carrec;
import models.colles.Colla;
import models.diades.Diada;
import models.locations.Ciutat;
import models.locations.Location;
import relationships.CastellDiada;

import java.util.HashMap;
import java.util.List;

public class BusinessFacade {
	private final SqlConnection connection = new SqlConnection();
	private final HashMap<String, String> session = new HashMap<>();

	private List<Ciutat> ciutats;
	private List<Location> locations;
	private List<Casteller> castellers;
	private List<Colla> colles;
	private List<Carrec> carrecs;
	private List<Estructura> estructures;
	private List<Pisos> pisos;
	private List<Reforcos> reforcos;
	private List<Rengla> rengles;
	private List<Castell> castells;
	private List<Diada> diades;
	private List<CastellDiada> castellsFets;

	public BusinessFacade() {
		session.put("activa", "FALSE");
	}

	public boolean isSessionActive() {
		return session.get("activa").equals("TRUE");
	}

	public void logIn(HashMap<String, String> credentials) throws WrongCredentialsException {
		session.put("identificador", credentials.get("identificador"));
		session.put("rol", connection.logIn(credentials));
		session.put("activa", "TRUE");
	}

	public void loadAll() {
		loadCiutats();
		loadCastellers();
		loadColles();
		loadCarrecs();
		loadCastells();
		loadDiades();
	}

	public List<Diada> getDiades() {
		return diades;
	}

	private void loadCiutats() {
		ciutats = new CiutatSqlDAO(connection.connection).loadAll();
		locations = new LocationSqlDAO(connection.connection).loadAll(ciutats);
	}

	private void loadCastellers() {
		castellers = new CastellerSqlDAO(connection.connection).loadAll();
		new RegistreSqlDAO(connection.connection).loadAll(castellers);
	}

	private void loadColles() {
		colles = new CollaSqlDAO(connection.connection).loadAll();
		new EsDeLaCollaSqlDAO(connection.connection).loadAll(castellers, colles);
		new CollaColorSqlDAO(connection.connection).loadAll(colles);
		new CollaFundacioSqlDAO(connection.connection).loadAll(colles);
		new CollaNomSqlDAO(connection.connection).loadAll(colles);
		new CollaAdrecaSqlDAO(connection.connection).loadAll(colles, ciutats);
	}

	private void loadCarrecs() {
		carrecs = new CarrecSqlDAO(connection.connection).loadAll();
		new TeCarrecSqlDAO(connection.connection).loadAll(castellers, colles, carrecs);
	}

	private void loadCastells() {
		estructures = new EstructuraSqlDAO(connection.connection).loadAll();
		pisos = new PisosSqlDAO(connection.connection).loadAll();
		reforcos = new ReforcosSqlDAO(connection.connection).loadAll();
		rengles = new RenglaSqlDAO(connection.connection).loadAll(estructures);
		castells = new CastellSqlDAO(connection.connection).loadAll(estructures, pisos, reforcos);
	}

	private void loadDiades() {
		diades = new DiadaSqlDAO(connection.connection).loadAll(locations);
		castellsFets = new CastellDiadaSqlDAO(connection.connection).loadAll(diades, castells, colles);
		new CastellLineUpSqlDAO(connection.connection).loadAll(castellers, castellsFets, rengles);
	}
}
