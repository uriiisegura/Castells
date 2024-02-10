package business;

import business.dto.CastellerDTO;
import business.dto.EsDeLaCollaDTO;
import business.dto.LogInDTO;
import config.DateParser;
import exceptions.UserIsNotLoggedInException;
import exceptions.ValidationException;
import exceptions.ValuelessEverException;
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
import relationships.EsDeLaColla;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusinessFacade {
	private final SqlConnection connection = new SqlConnection();
	private final Session session = new Session();

	private final CiutatSqlDAO ciutatSqlDAO = new CiutatSqlDAO(connection.connection);
	private final LocationSqlDAO locationSqlDAO = new LocationSqlDAO(connection.connection);
	private final CastellerSqlDAO castellerSqlDAO = new CastellerSqlDAO(connection.connection);
	private final RegistreSqlDAO registreSqlDAO = new RegistreSqlDAO(connection.connection);
	private final CollaSqlDAO collaSqlDAO = new CollaSqlDAO(connection.connection);
	private final EsDeLaCollaSqlDAO esDeLaCollaSqlDAO = new EsDeLaCollaSqlDAO(connection.connection);
	private final CollaColorSqlDAO collaColorSqlDAO = new CollaColorSqlDAO(connection.connection);
	private final CollaFundacioSqlDAO collaFundacioSqlDAO = new CollaFundacioSqlDAO(connection.connection);
	private final CollaNomSqlDAO collaNomSqlDAO = new CollaNomSqlDAO(connection.connection);
	private final CollaAdrecaSqlDAO collaAdrecaSqlDAO = new CollaAdrecaSqlDAO(connection.connection);
	private final CarrecSqlDAO carrecSqlDAO = new CarrecSqlDAO(connection.connection);
	private final TeCarrecSqlDAO teCarrecSqlDAO = new TeCarrecSqlDAO(connection.connection);
	private final EstructuraSqlDAO estructuraSqlDAO = new EstructuraSqlDAO(connection.connection);
	private final PisosSqlDAO pisosSqlDAO = new PisosSqlDAO(connection.connection);
	private final ReforcosSqlDAO reforcosSqlDAO = new ReforcosSqlDAO(connection.connection);
	private final RenglaSqlDAO renglaSqlDAO = new RenglaSqlDAO(connection.connection);
	private final CastellSqlDAO castellSqlDAO = new CastellSqlDAO(connection.connection);
	private final DiadaSqlDAO diadaSqlDAO = new DiadaSqlDAO(connection.connection);
	private final CastellDiadaSqlDAO castellDiadaSqlDAO = new CastellDiadaSqlDAO(connection.connection);
	private final CastellLineUpSqlDAO castellLineUpSqlDAO = new CastellLineUpSqlDAO(connection.connection);

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

	public void logIn(LogInDTO credentials) throws WrongCredentialsException {
		session.identificador = credentials.getIdentifier();
		session.rol = connection.logIn(credentials.getIdentifier(), credentials.getPassword());
		session.activa = true;
	}

	public boolean isSessionActive() {
		return session.activa;
	}

	public void loadAll() {
		loadCiutats();
		loadCastellers();
		loadColles();
		loadCarrecs();
		loadCastells();
		loadDiades();
	}

	public Casteller validateAndAddCasteller(CastellerDTO casteller) throws ValidationException {
		for (Casteller c : castellers) {
			if (c.getDni().equals(casteller.getDni())) {
				throw new ValidationException("Ja existeix un casteller amb aquest DNI/NIE.");
			}
		}

		if (!casteller.getSexe().matches("^(home|dona|no binari)$")) {
			throw new ValidationException("El sexe ha de ser 'home', 'dona' o 'no binari'.");
		}

		LocalDate dataNaixement;
		try {
			dataNaixement = LocalDate.parse(casteller.getDataNaixement());
		} catch (DateTimeParseException e) {
			throw new ValidationException("La data de naixement ha de ser en format yyyy-mm-dd.");
		}

		LocalDate dataDefuncio = null;
		if (casteller.getDataDefuncio() != null) {
			try {
				dataDefuncio = LocalDate.parse(casteller.getDataDefuncio());
			} catch (DateTimeParseException e) {
				throw new ValidationException("La data de defunció ha de ser en format yyyy-mm-dd.");
			}
		}

		Casteller newCasteller = new Casteller(
				casteller.getDni(),
				casteller.getNom(),
				casteller.getCognom1(),
				casteller.getCognom2(),
				casteller.getSexe(),
				dataNaixement,
				dataDefuncio
		);
		castellerSqlDAO.addCasteller(newCasteller);
		castellers.add(newCasteller);

		return newCasteller;
	}

	public HashMap<String, Colla> getCurrentCollaNames() {
		HashMap<String, Colla> collaNames = new HashMap<>();
		for (Colla colla : colles) {
			String nom;
			try {
				nom = colla.getCurrentNom();
			} catch (Exception e) {
				try {
					nom = colla.getLastName();
				} catch (ValuelessEverException ignored) {
					continue;
				}
			}
			collaNames.put(nom, colla);
		}
		return collaNames;
	}

	public void validateAndAddCastellerToColla(Casteller casteller, Colla colla, EsDeLaCollaDTO esDeLaColla) throws ValidationException {
		LocalDate desDe;
		try {
			desDe = LocalDate.parse(esDeLaColla.getDesDe());
		} catch (DateTimeParseException e) {
			throw new ValidationException("La data d'entrada a la colla ha de ser en format yyyy-mm-dd.");
		}

		LocalDate finsA = null;
		if (esDeLaColla.getFinsA() != null) {
			try {
				finsA = LocalDate.parse(esDeLaColla.getFinsA());
			} catch (DateTimeParseException e) {
				throw new ValidationException("La data de sortida de la colla ha de ser en format yyyy-mm-dd.");
			}
		}

		EsDeLaColla newEsDeLaColla = new EsDeLaColla(casteller, colla, desDe, finsA, esDeLaColla.getMalnom());
		esDeLaCollaSqlDAO.addEsDeLaColla(newEsDeLaColla);
		colla.addCasteller(newEsDeLaColla);
		casteller.addColla(newEsDeLaColla);
	}

	public List<CastellDiada> getOwnCastells() throws UserIsNotLoggedInException {
		if (!isSessionActive()) {
			throw new UserIsNotLoggedInException();
		}

		List<CastellDiada> ownCastells = new ArrayList<>();
		for (CastellDiada castellDiada : castellsFets) {
			if (castellDiada.hasCasteller(session.identificador))
				ownCastells.add(castellDiada);
		}
		return ownCastells;
	}

	private void loadCiutats() {
		ciutats = ciutatSqlDAO.loadAll();
		locations = locationSqlDAO.loadAll(ciutats);
	}

	private void loadCastellers() {
		castellers = castellerSqlDAO.loadAll();
		registreSqlDAO.loadAll(castellers);
	}

	private void loadColles() {
		colles = collaSqlDAO.loadAll();
		esDeLaCollaSqlDAO.loadAll(castellers, colles);
		collaColorSqlDAO.loadAll(colles);
		collaFundacioSqlDAO.loadAll(colles);
		collaNomSqlDAO.loadAll(colles);
		collaAdrecaSqlDAO.loadAll(colles, ciutats);
	}

	private void loadCarrecs() {
		carrecs = carrecSqlDAO.loadAll();
		teCarrecSqlDAO.loadAll(castellers, colles, carrecs);
	}

	private void loadCastells() {
		estructures = estructuraSqlDAO.loadAll();
		pisos = pisosSqlDAO.loadAll();
		reforcos = reforcosSqlDAO.loadAll();
		rengles = renglaSqlDAO.loadAll(estructures);
		castells = castellSqlDAO.loadAll(estructures, pisos, reforcos);
	}

	private void loadDiades() {
		diades = diadaSqlDAO.loadAll(locations);
		castellsFets = castellDiadaSqlDAO.loadAll(diades, castells, colles);
		castellLineUpSqlDAO.loadAll(castellers, castellsFets, rengles);
	}

	private static class Session {
		private String identificador = null;
		private String rol = null;
		private boolean activa = false;
	}
}
