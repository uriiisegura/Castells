package presentation;

import business.BusinessFacade;
import exceptions.NotAllowedException;
import exceptions.UserIsNotLoggedInException;
import exceptions.ValidationException;
import exceptions.WrongCredentialsException;
import models.castellers.Casteller;
import models.colles.Colla;
import models.locations.Ciutat;
import models.locations.Pais;
import presentation.console.ConsoleUiManager;
import presentation.options.AddDataMenuOptions;
import presentation.options.MainMenuOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UiController {
	private final BusinessFacade businessFacade;
	private final UiManager uiManager = new ConsoleUiManager();

	public UiController(BusinessFacade businessFacade) {
		this.businessFacade = businessFacade;
	}

	public void start() {
		uiManager.start();

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
					case ADD_DATA:
						switch ((AddDataMenuOptions) uiManager.askMenuOption(AddDataMenuOptions.values())) {
							case ADD_CASTELLER:
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
						break;
					case VIEW_DATA:
						// TODO:
						try {
							uiManager.showCastells(businessFacade.getOwnCastells());
						} catch (UserIsNotLoggedInException e) {
							uiManager.showError(e.getMessage());
							logInAgain = true;
						}
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

		if (uiManager.askBoolean("Vols afegir aquest casteller a alguna colla? (s/n): ")) {
			boolean continueAdding;
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

				validData = false;
				do {
					try {
						businessFacade.validateAndAddCastellerToColla(newCasteller, colla, uiManager.askEsDeLaColla());
						validData = true;
					} catch (ValidationException e) {
						uiManager.showError(e.getMessage());
					}
				} while (!validData);

				continueAdding = uiManager.askBoolean("Vols afegir aquest casteller a una altra colla? (s/n): ");
			} while (continueAdding);
		}

		return newCasteller;
	}

	private Colla createColla() throws UserIsNotLoggedInException, NotAllowedException {
		boolean validData = false;
		boolean addMore;
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

		validData = false;
		do {
			do {
				try {
					businessFacade.validateAndAddCollaFundacio(newColla, uiManager.askCollaFundacio());
					validData = true;
				} catch (ValidationException e) {
					uiManager.showError(e.getMessage());
				}
			} while (!validData);
			addMore = uiManager.askBoolean("Vols afegir una altra fundació per a la colla? (s/n): ");
		} while (addMore);

		validData = false;
		do {
			do {
				try {
					businessFacade.validateAndAddCollaNom(newColla, uiManager.askCollaNom());
					validData = true;
				} catch (ValidationException e) {
					uiManager.showError(e.getMessage());
				}
			} while (!validData);
			addMore = uiManager.askBoolean("Vols afegir un altre nom per a la colla? (s/n): ");
		} while (addMore);

		addMore = uiManager.askBoolean("Vols definir un color per a la colla? (s/n): ");
		while (addMore) {
			validData = false;
			do {
				try {
					businessFacade.validateAndAddCollaColor(newColla, uiManager.askCollaColor());
					validData = true;
				} catch (ValidationException e) {
					uiManager.showError(e.getMessage());
				}
			} while (!validData);
			addMore = uiManager.askBoolean("Vols definir un altre color per a la colla? (s/n): ");
		}

		addMore = uiManager.askBoolean("Vols afegir una adreça per a la colla? (s/n): ");
		while (addMore) {
			HashMap<String, Pais> paissos = businessFacade.getPaissos();
			List<String> paissosNames = new ArrayList<>(paissos.keySet());
			int paisOption = uiManager.askOptionFromList("A quin país pertany la ciutat?", paissosNames, "País: ");
			Pais pais = paissos.get(paissosNames.get(paisOption));
			validData = false;
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
					businessFacade.validateAndAddCollaAdreca(newColla, ciutat, uiManager.askCollaAdreca());
					validData = true;
				} catch (ValidationException e) {
					uiManager.showError(e.getMessage());
				}
			} while (!validData);
			addMore = uiManager.askBoolean("Vols afegir una altra adreça per a la colla? (s/n): ");
		}

		return newColla;
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
