package presentation.swing;

import business.BusinessFacade;
import business.dto.CastellerDTO;
import business.dto.EsDeLaCollaDTO;
import business.dto.LogInDTO;
import business.dto.PeriodeDTO;
import exceptions.NotAllowedException;
import exceptions.UserIsNotLoggedInException;
import exceptions.ValidationException;
import exceptions.WrongCredentialsException;
import models.Periode;
import models.castellers.Casteller;
import models.colles.Colla;
import presentation.Controller;
import presentation.swing.views.LogInView;
import presentation.swing.views.MainView;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

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

		if (mainView.askBoolean("Vols afegir el casteller a una colla?"))
			addCastellerToColla(newCasteller);
		else
			mainView.goHome();
	}

	public void addCastellerToColla(EsDeLaCollaDTO esDeLaColla, String castellerDni, String collaId) {
		Casteller casteller = null;
		try {
			casteller = businessFacade.validateAndAddCastellerToColla(esDeLaColla, castellerDni, collaId);
		} catch (ValidationException e) {
			mainView.showError(e.getMessage());
			return;
		} catch (UserIsNotLoggedInException | NotAllowedException e) {
			logOut();
		}

		mainView.showMessage("Casteller afegit a la colla correctament.");

		if (mainView.askBoolean("Vols afegir el casteller a una altra colla?"))
			addCastellerToColla(casteller);
		else
			mainView.goHome();
	}

	public void logOut() {
		businessFacade.logOut();
		mainView.dispose();
		start();
	}

	private void addCastellerToColla(Casteller casteller) {
		CastellerDTO castellerDTO = new CastellerDTO(casteller);
		try {
			HashMap<String, String> colles = businessFacade.getCollaNamesAt(
					new PeriodeDTO(castellerDTO.getDesDe(), castellerDTO.getFinsA())
			);
			mainView.addCastellerToColla(castellerDTO, colles);
		} catch (ValidationException ignored) {}
	}
}
