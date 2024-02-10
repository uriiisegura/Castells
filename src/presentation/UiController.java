package presentation;

import business.BusinessFacade;
import exceptions.UserIsNotLoggedInException;
import exceptions.ValidationException;
import exceptions.WrongCredentialsException;
import models.castellers.Casteller;
import models.colles.Colla;
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
					logInAgain = false;
				} catch (WrongCredentialsException e) {
					uiManager.showError(e.getMessage());
				}
			}
			businessFacade.loadAll();

			MainMenuOptions mainMenuOption;
			do {
				mainMenuOption = (MainMenuOptions) uiManager.askMenuOption(MainMenuOptions.values());
				switch (mainMenuOption) {
					case ADD_DATA:
						switch ((AddDataMenuOptions) uiManager.askMenuOption(AddDataMenuOptions.values())) {
							case ADD_CASTELLER:
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
									boolean continueAdding = false;
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
											if (collaOption == collaNames.size())
												createColla = true;
											else
												colla = colles.get(collaNames.get(collaOption));
										}
										if (createColla) {
											// TODO: crear una nova colla
											// colla = newColla;
										}

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

								break;
						}
						break;
					case EDIT_DATA:
						break;
					case VIEW_DATA:
						break;
					case EXIT:
						break;
				}
			} while (mainMenuOption != MainMenuOptions.EXIT);

			// TODO:
			try {
				uiManager.showCastells(businessFacade.getOwnCastells());
			} catch (UserIsNotLoggedInException e) {
				logInAgain = true;
			}
		}
	}
}
