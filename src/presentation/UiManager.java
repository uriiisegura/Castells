package presentation;

import models.diades.Diada;

import java.util.HashMap;
import java.util.List;

public interface UiManager {
	void start();
	HashMap<String, String> logIn();
	void wrongCredentials(String message);
	void showDiades(List<Diada> diades);
}
