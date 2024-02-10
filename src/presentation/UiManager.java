package presentation;

import relationships.CastellDiada;

import java.util.HashMap;
import java.util.List;

public interface UiManager {
	void start();
	HashMap<String, String> logIn();
	void wrongCredentials(String message);
	void showCastells(List<CastellDiada> castells);
}
