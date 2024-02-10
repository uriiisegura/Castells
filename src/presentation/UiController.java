package presentation;

import business.BusinessFacade;
import exceptions.UserIsNotLoggedInException;
import exceptions.WrongCredentialsException;
import presentation.console.ConsoleUiManager;

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
					uiManager.wrongCredentials(e.getMessage());
				}
			}

			businessFacade.loadAll();
			try {
				uiManager.showCastells(businessFacade.getOwnCastells());
			} catch (UserIsNotLoggedInException e) {
				logInAgain = true;
			}
		}
	}
}
