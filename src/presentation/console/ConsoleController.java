package presentation.console;

import business.BusinessFacade;
import exceptions.NotAllowedException;
import exceptions.UserIsNotLoggedInException;
import exceptions.ValidationException;
import exceptions.WrongCredentialsException;
import models.castellers.Casteller;
import models.colles.Colla;
import models.locations.Ciutat;
import models.locations.Pais;
import presentation.Controller;
import presentation.options.CreateDataMenuOptions;
import presentation.options.MainMenuOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsoleController implements Controller {
	private final BusinessFacade businessFacade;
	private final ConsoleUiManager uiManager = new ConsoleUiManager();

	public ConsoleController(BusinessFacade businessFacade) {
		this.businessFacade = businessFacade;
	}

	public void start() {
		boolean logInAgain = true;
		while (logInAgain) {
			while (!businessFacade.isSessionActive()) {
				try {
					businessFacade.logIn(uiManager.logIn());
					businessFacade.loadAll();
					logInAgain = false;
				} catch (WrongCredentialsException e) {
					uiManager.showError(e.getMessage());
				} catch (NotAllowedException e) {
					// TODO
				}
			}

			MainMenuOptions mainMenuOption;
			do {
				mainMenuOption = (MainMenuOptions) uiManager.askMenuOption(MainMenuOptions.values());
				switch (mainMenuOption) {
					case CREATE_DATA:
						switch ((CreateDataMenuOptions) uiManager.askMenuOption(CreateDataMenuOptions.values())) {
							case CREATE_CASTELLER:
								try {
									createCasteller();
								} catch (UserIsNotLoggedInException | NotAllowedException e) {
									uiManager.showError(e.getMessage());
									logInAgain = true;
								}
								break;
							case CREATE_COLLA:
								try {
									createColla();
								} catch (UserIsNotLoggedInException | NotAllowedException e) {
									uiManager.showError(e.getMessage());
									logInAgain = true;
								}
								break;
						}
						break;
					case EDIT_DATA:
						// TODO:
						break;
					case VIEW_DATA:
						// TODO:
						break;
					case EXIT:
						break;
				}
			} while (mainMenuOption != MainMenuOptions.EXIT && !logInAgain);
		}
	}

	private Casteller createCasteller() throws UserIsNotLoggedInException, NotAllowedException {
		boolean validData = false;
		Casteller newCasteller = null;

		do {
			try {
				newCasteller = businessFacade.validateAndAddCasteller(uiManager.askNewCasteller());
				validData = true;
			} catch (ValidationException e) {
				uiManager.showError(e.getMessage());
			}
		} while (!validData);

		uiManager.showMessage("Casteller afegit correctament.");

		if (uiManager.askBoolean("Vols afegir aquest casteller a alguna colla? (s/n): "))
			addCastellerToColla(newCasteller);

		return newCasteller;
	}

	private void addCastellerToColla(Casteller casteller) throws UserIsNotLoggedInException, NotAllowedException {
		boolean addMore;
		do {
			boolean createColla = false;
			Colla colla = null;
			HashMap<String, Colla> colles = businessFacade.getCurrentCollaNames();
			if (colles.isEmpty()) {
				createColla = uiManager.askBoolean("No hi ha cap colla. Vols crear-ne una? (s/n): ");
			} else {
				List<String> collaNames = new ArrayList<>(colles.keySet());
				collaNames.add("Crear una nova colla");
				int collaOption = uiManager.askOptionFromList("A quina colla vols afegir el casteller?", collaNames, "Colla: ");
				if (collaOption == collaNames.size() - 1)
					createColla = true;
				else
					colla = colles.get(collaNames.get(collaOption));
			}
			if (createColla)
				colla = createColla();

			boolean validData = false;
			do {
				try {
					businessFacade.validateAndAddCastellerToColla(casteller, colla, uiManager.askEsDeLaColla());
					validData = true;
				} catch (ValidationException e) {
					uiManager.showError(e.getMessage());
				}
			} while (!validData);

			addMore = uiManager.askBoolean("Vols afegir aquest casteller a una altra colla? (s/n): ");
		} while (addMore);
	}

	private Colla createColla() throws UserIsNotLoggedInException, NotAllowedException {
		boolean validData = false;
		Colla newColla = null;

		do {
			try {
				newColla = businessFacade.validateAndAddColla(uiManager.askNewColla());
				validData = true;
			} catch (ValidationException e) {
				uiManager.showError(e.getMessage());
			}
		} while (!validData);

		uiManager.showMessage("Colla creada correctament.");

		addCollaFundation(newColla);
		addCollaName(newColla);

		if (uiManager.askBoolean("Vols definir un color per a la colla? (s/n): "))
			addCollaColor(newColla);

		if (uiManager.askBoolean("Vols afegir una adreça per a la colla? (s/n): "))
			addCollaAdreca(newColla);

		return newColla;
	}

	private void addCollaFundation(Colla colla) throws UserIsNotLoggedInException, NotAllowedException {
		boolean addMore;
		do {
			boolean validData = false;
			do {
				try {
					businessFacade.validateAndAddCollaFundacio(colla, uiManager.askCollaFundacio());
					validData = true;
				} catch (ValidationException e) {
					uiManager.showError(e.getMessage());
				}
			} while (!validData);
			addMore = uiManager.askBoolean("Vols afegir una altra fundació per a la colla? (s/n): ");
		} while (addMore);
	}

	private void addCollaName(Colla colla) throws UserIsNotLoggedInException, NotAllowedException {
		boolean addMore;
		do {
			boolean validData = false;
			do {
				try {
					businessFacade.validateAndAddCollaNom(colla, uiManager.askCollaNom());
					validData = true;
				} catch (ValidationException e) {
					uiManager.showError(e.getMessage());
				}
			} while (!validData);
			addMore = uiManager.askBoolean("Vols afegir un altre nom per a la colla? (s/n): ");
		} while (addMore);
	}

	private void addCollaColor(Colla colla) throws UserIsNotLoggedInException, NotAllowedException {
		boolean addMore;
		do {
			boolean validData = false;
			do {
				try {
					businessFacade.validateAndAddCollaColor(colla, uiManager.askCollaColor());
					validData = true;
				} catch (ValidationException e) {
					uiManager.showError(e.getMessage());
				}
			} while (!validData);
			addMore = uiManager.askBoolean("Vols definir un altre color per a la colla? (s/n): ");
		} while (addMore);
	}

	private void addCollaAdreca(Colla colla) throws UserIsNotLoggedInException, NotAllowedException {
		boolean addMore;
		do {
			HashMap<String, Pais> paissos = businessFacade.getPaissos();
			List<String> paissosNames = new ArrayList<>(paissos.keySet());
			int paisOption = uiManager.askOptionFromList("A quin país pertany la ciutat?", paissosNames, "País: ");
			Pais pais = paissos.get(paissosNames.get(paisOption));
			boolean validData = false;
			do {
				List<String> ciutatsNames = new ArrayList<>(pais.getCiutats().keySet());
				ciutatsNames.add("Crear una nova ciutat");
				int ciutatOption = uiManager.askOptionFromList("A quina ciutat pertany la colla?", ciutatsNames, "Ciutat: ");
				Ciutat ciutat;
				if (ciutatOption == ciutatsNames.size() - 1)
					ciutat = createCiutat(pais);
				else
					ciutat = pais.getCiutats().get(ciutatsNames.get(ciutatOption));

				try {
					businessFacade.validateAndAddCollaAdreca(colla, ciutat, uiManager.askCollaAdreca());
					validData = true;
				} catch (ValidationException e) {
					uiManager.showError(e.getMessage());
				}
			} while (!validData);
			addMore = uiManager.askBoolean("Vols afegir una altra adreça per a la colla? (s/n): ");
		} while (addMore);
	}

	private Ciutat createCiutat(Pais pais) throws UserIsNotLoggedInException, NotAllowedException {
		boolean validData = false;
		Ciutat newCiutat = null;
		do {
			try {
				newCiutat = businessFacade.validateAndAddCiutat(pais, uiManager.askNewCiutat());
				validData = true;
			} catch (ValidationException e) {
				uiManager.showError(e.getMessage());
			}
		} while (!validData);

		uiManager.showMessage("Ciutat afegida correctament.");

		return newCiutat;
	}
}
