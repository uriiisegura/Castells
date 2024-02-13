package business;

import business.dto.*;
import exceptions.*;
import models.Periode;
import models.colles.CollaConvencional;
import models.colles.CollaUniversitaria;
import models.locations.Pais;
import persistence.dao.*;
import persistence.SqlConnection;
import models.castellers.Casteller;
import models.castells.*;
import models.colles.Carrec;
import models.colles.Colla;
import models.diades.Diada;
import models.locations.Ciutat;
import models.locations.Location;
import models.relationships.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

public class BusinessFacade {
	private final SqlConnection connection = new SqlConnection();
	private final Session session = new Session();

	private final PaisSqlDAO paisSqlDAO = new PaisSqlDAO(connection.connection);
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
	private final EstaPuntuatSqlDAO estaPuntuatSqlDAO = new EstaPuntuatSqlDAO(connection.connection);
	private final DiadaSqlDAO diadaSqlDAO = new DiadaSqlDAO(connection.connection);
	private final CastellDiadaSqlDAO castellDiadaSqlDAO = new CastellDiadaSqlDAO(connection.connection);
	private final CastellLineUpSqlDAO castellLineUpSqlDAO = new CastellLineUpSqlDAO(connection.connection);

	private List<Pais> paissos;
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
	private List<EstaPuntuat> puntuacions;
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

	public void loadAll() throws NotAllowedException {
		if (!session.rol.equals("administrador")) {
			// TODO:
			session.activa = false;
			throw new NotAllowedException();
		}
		loadLocations();
		loadCastellers();
		loadColles();
		loadCarrecs();
		loadCastells();
		loadDiades();
	}

	public Casteller validateAndAddCasteller(CastellerDTO casteller) throws UserIsNotLoggedInException, NotAllowedException, ValidationException {
		if (!isSessionActive())
			throw new UserIsNotLoggedInException();
		if (!session.rol.equals("administrador"))
			throw new NotAllowedException();

		for (Casteller c : castellers) {
			if (c.getDni().equals(casteller.getDni())) {
				throw new ValidationException("Ja existeix un casteller amb aquest DNI/NIE.");
			}
		}

		if (!casteller.getSexe().matches("^(home|dona|no binari)$")) {
			throw new ValidationException("El sexe ha de ser 'home', 'dona' o 'no binari'.");
		}

		Periode periode = validatePeriode(casteller.getDataNaixement(), casteller.getDataDefuncio(), true);

		Casteller newCasteller = new Casteller(
				casteller.getDni(),
				casteller.getNom(),
				casteller.getCognom1(),
				casteller.getCognom2(),
				casteller.getSexe(),
				periode.getDesDe(),
				periode.getFinsA()
		);
		castellerSqlDAO.add(newCasteller);
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

	public void validateAndAddCastellerToColla(Casteller casteller, Colla colla, EsDeLaCollaDTO esDeLaColla) throws UserIsNotLoggedInException, NotAllowedException, ValidationException {
		if (!isSessionActive())
			throw new UserIsNotLoggedInException();
		if (!session.rol.equals("administrador"))
			throw new NotAllowedException();

		Periode periode = validatePeriode(esDeLaColla.getDesDe(), esDeLaColla.getFinsA(), true);
		EsDeLaColla newEsDeLaColla = new EsDeLaColla(casteller, colla, periode.getDesDe(), periode.getFinsA(), esDeLaColla.getMalnom());

		try {
			List<EsDeLaColla> periods = casteller.getPeriodsInColla(colla);
			periods.add(newEsDeLaColla);
			if (periodsOverlap(periods))
				throw new ValidationException("Els períodes d'un casteller a una mateix colla no es poden solapar.");
		} catch (ValuelessEverException ignored) {}

		esDeLaCollaSqlDAO.add(newEsDeLaColla);
		colla.addCasteller(newEsDeLaColla);
		casteller.addColla(newEsDeLaColla);
	}

	public Colla validateAndAddColla(CollaDTO colla) throws UserIsNotLoggedInException, NotAllowedException, ValidationException {
		if (!isSessionActive())
			throw new UserIsNotLoggedInException();
		if (!session.rol.equals("administrador"))
			throw new NotAllowedException();

		for (Colla c : colles)
			if (c.getId().equals(colla.getId()))
				throw new ValidationException("Ja existeix una colla amb aquest id.");

		Colla newColla = colla.esUniversitaria() ? new CollaUniversitaria(colla.getId()) : new CollaConvencional(colla.getId());
		collaSqlDAO.add(newColla);
		colles.add(newColla);

		return newColla;
	}

	public void validateAndAddCollaNom(Colla colla, CollaNomDTO collaNom) throws UserIsNotLoggedInException, NotAllowedException, ValidationException {
		if (!isSessionActive())
			throw new UserIsNotLoggedInException();
		if (!session.rol.equals("administrador"))
			throw new NotAllowedException();

		Periode periode = validatePeriode(collaNom.getDesDe(), collaNom.getFinsA(), true);
		CollaNom newCollaNom = new CollaNom(collaNom.getNom(), periode.getDesDe(), periode.getFinsA());

		try {
			List<CollaNom> periods = colla.getNoms();
			periods.add(newCollaNom);
			if (periodsOverlap(periods))
				throw new ValidationException("Una colla no pot tenir dos noms al mateix temps.");
		} catch (ValuelessEverException ignored) {}

		collaNomSqlDAO.add(colla.getId(), newCollaNom);
		colla.addNom(newCollaNom);
	}

	public void validateAndAddCollaFundacio(Colla colla, PeriodeDTO periode) throws UserIsNotLoggedInException, NotAllowedException, ValidationException {
		if (!isSessionActive())
			throw new UserIsNotLoggedInException();
		if (!session.rol.equals("administrador"))
			throw new NotAllowedException();

		Periode newPeriode = validatePeriode(periode.getDesDe(), periode.getFinsA(), true);
		CollaFundacio newCollaFundacio = new CollaFundacio(newPeriode.getDesDe(), newPeriode.getFinsA());

		try {
			List<CollaFundacio> periods = colla.getFundacions();
			periods.add(newCollaFundacio);
			if (periodsOverlap(periods))
				throw new ValidationException("Una colla no pot tenir dues fundacions al mateix temps.");
		} catch (ValuelessEverException ignored) {}

		collaFundacioSqlDAO.add(colla.getId(), newCollaFundacio);
		colla.addFundacio(newCollaFundacio);
	}

	public void validateAndAddCollaColor(Colla colla, CollaColorDTO collaColor) throws UserIsNotLoggedInException, NotAllowedException, ValidationException {
		if (!isSessionActive())
			throw new UserIsNotLoggedInException();
		if (!session.rol.equals("administrador"))
			throw new NotAllowedException();

		Periode newPeriode = validatePeriode(collaColor.getDesDe(), collaColor.getFinsA(), true);

		Color color;
		try {
			color = Color.decode(collaColor.getColor());
		} catch (NumberFormatException e) {
			throw new ValidationException("El color ha de ser en format hexadecimal (#rrggbb).");
		}

		CollaColor newCollaColor = new CollaColor(color, newPeriode.getDesDe(), newPeriode.getFinsA());

		try {
			List<CollaColor> periods = colla.getColors();
			periods.add(newCollaColor);
			if (periodsOverlap(periods))
				throw new ValidationException("Una colla no pot tenir dos colors al mateix temps.");
		} catch (ValuelessEverException ignored) {}

		collaColorSqlDAO.add(colla.getId(), newCollaColor);
		colla.addColor(newCollaColor);
	}

	public HashMap<String, Pais> getPaissos() {
		HashMap<String, Pais> paissos = new HashMap<>();
		for (Pais pais : this.paissos) {
			paissos.put(pais.getNom(), pais);
		}
		return paissos;
	}

	public void validateAndAddCollaAdreca(Colla colla, Ciutat ciutat, CollaAdrecaDTO collaAdreca) throws UserIsNotLoggedInException, NotAllowedException, ValidationException {
		if (!isSessionActive())
			throw new UserIsNotLoggedInException();
		if (!session.rol.equals("administrador"))
			throw new NotAllowedException();

		Periode periode = validatePeriode(collaAdreca.getDesDe(), collaAdreca.getFinsA(), true);
		CollaAdreca newCollaAdreca = new CollaAdreca(collaAdreca.getAdreca(), ciutat, periode.getDesDe(), periode.getFinsA());

		try {
			List<CollaAdreca> periods = colla.getAdreces();
			periods.add(newCollaAdreca);
			if (periodsOverlap(periods))
				throw new ValidationException("Una colla no pot tenir dues adreces al mateix temps.");
		} catch (ValuelessEverException ignored) {}

		collaAdrecaSqlDAO.add(colla.getId(), newCollaAdreca);
		colla.addAdreca(newCollaAdreca);
	}

	public Ciutat validateAndAddCiutat(Pais pais, CiutatDTO ciutat) throws UserIsNotLoggedInException, NotAllowedException, ValidationException {
		if (!isSessionActive())
			throw new UserIsNotLoggedInException();
		if (!session.rol.equals("administrador"))
			throw new NotAllowedException();

		for (Ciutat c : ciutats)
			if (c.getNom().equals(ciutat.getNom()) && c.getPais() == pais)
				throw new ValidationException("Ja existeix la ciutat.");

		Ciutat newCiutat = new Ciutat(ciutat.getNom(), pais);
		ciutatSqlDAO.add(newCiutat);
		ciutats.add(newCiutat);

		return newCiutat;
	}

	private void loadLocations() {
		paissos = paisSqlDAO.loadAll();
		ciutats = ciutatSqlDAO.loadAll(paissos);
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
		puntuacions = estaPuntuatSqlDAO.loadAll(castells);
	}

	private static Periode validatePeriode(String dataInici, String dataFi, boolean canBeEndless) throws ValidationException {
		LocalDate inici;
		try {
			inici = LocalDate.parse(dataInici);
		} catch (DateTimeParseException e) {
			throw new ValidationException("Totes les dates ha de ser en format yyyy-mm-dd.");
		}

		if (inici.isAfter(LocalDate.now())) {
			throw new ValidationException("La data d'inici no pot ser posterior a la data actual.");
		}

		LocalDate fi = null;
		if (!canBeEndless || dataFi != null) {
			try {
				fi = LocalDate.parse(dataFi);
			} catch (DateTimeParseException e) {
				throw new ValidationException("Totes les dates ha de ser en format yyyy-mm-dd.");
			}

			if (fi.isBefore(inici)) {
				throw new ValidationException("La data d'inici no pot ser posterior a la data de finalització.");
			}

			if (fi.isAfter(LocalDate.now())) {
				throw new ValidationException("La data de finalització no pot ser posterior a la data actual.");
			}
		}

		return new Periode(inici, fi);
	}

	private static boolean periodsOverlap(List<? extends Periode> periods) {
		for (int i = 0; i < periods.size(); i++) {
			for (int j = i + 1; j < periods.size(); j++) {
				if (periods.get(i).overlaps(periods.get(j)))
					return true;
			}
		}
		return false;
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
