package presentation.swing;

import business.BusinessFacade;
import business.dto.CastellerDTO;
import business.dto.LogInDTO;
import exceptions.NotAllowedException;
import exceptions.UserIsNotLoggedInException;
import exceptions.ValidationException;
import exceptions.WrongCredentialsException;
import models.castellers.Casteller;
import presentation.Controller;
import presentation.swing.views.LogInView;
import presentation.swing.views.MainView;

import javax.swing.*;

public class SwingController implements Controller {
	private final BusinessFacade businessFacade;

	private final LogInView logInView = new LogInView(this);
	private final MainView mainView = new MainView(this);

	public SwingController(BusinessFacade businessFacade) {
		this.businessFacade = businessFacade;
	}

	@Override
	public void start() {
		logInView.setDefaultLogInInfo(businessFacade.getLogInInfo());
		SwingUtilities.invokeLater(logInView::start);
	}

	public void logIn(LogInDTO logIn, boolean saveLogInInfo) {
		boolean logInAgain = true;
		try {
			businessFacade.logIn(logIn);
			if (saveLogInInfo)
				businessFacade.saveLogInInfo(logIn);
			else
				businessFacade.clearLogInInfo();
			businessFacade.loadAll();
			logInAgain = false;
		} catch (WrongCredentialsException e) {
			logInView.showError(e.getMessage());
		} catch (NotAllowedException e) {
			// TODO
		}

		if (logInAgain) {
			logInView.start();
			return;
		}

		logInView.dispose();
		SwingUtilities.invokeLater(mainView::start);
	}

	public void createCasteller(CastellerDTO casteller) {
		Casteller newCasteller = null;

		try {
			newCasteller = businessFacade.validateAndAddCasteller(casteller);
		} catch (ValidationException  e) {
			mainView.showError(e.getMessage());
			return;
		} catch (UserIsNotLoggedInException | NotAllowedException e) {
			logOut();
		}

		mainView.showMessage("Casteller afegit correctament.");
	}

	public void logOut() {
		businessFacade.logOut();
		mainView.dispose();
		start();
	}
}
