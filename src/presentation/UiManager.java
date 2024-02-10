package presentation;

import java.util.HashMap;

public interface UiManager {
	void start();
	HashMap<String, String> logIn();
	void wrongCredentials(String message);
}
