package presentation;

import business.BusinessFacade;
import exceptions.WrongCredentialsException;

public class UiController {
	private final BusinessFacade businessFacade;
	private final UiManager uiManager;

	public UiController(BusinessFacade businessFacade, UiManager uiManager) {
		this.businessFacade = businessFacade;
		this.uiManager = uiManager;
	}

	public void start() {
		uiManager.start();

		while (!businessFacade.isSessionActive()) {
			try {
				businessFacade.logIn(uiManager.logIn());
			} catch (WrongCredentialsException e) {
				uiManager.wrongCredentials(e.getMessage());
			}
		}
	}
}
