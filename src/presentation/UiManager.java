package presentation;

import business.dto.*;
import presentation.options.MenuOption;
import relationships.CastellDiada;

import java.util.List;

public interface UiManager {
	void start();
	LogInDTO logIn();
	void showError(String message);
	MenuOption askMenuOption(MenuOption[] options);
	CastellerDTO askNewCasteller();
	void showMessage(String message);
	boolean askBoolean(String message);
	int askOptionFromList(String title, List<String> options, String message);
	EsDeLaCollaDTO askEsDeLaColla();
	CollaDTO askNewColla();
	CollaNomDTO askCollaNom();
	PeriodeDTO askCollaFundacio();
	CollaColorDTO askCollaColor();
	CollaAdrecaDTO askCollaAdreca();
	CiutatDTO askNewCiutat();

	void showCastells(List<CastellDiada> castells);
}
