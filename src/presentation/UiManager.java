package presentation;

import business.dto.CastellerDTO;
import business.dto.EsDeLaCollaDTO;
import business.dto.LogInDTO;
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

	void showCastells(List<CastellDiada> castells);
}
