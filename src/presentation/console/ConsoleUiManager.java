package presentation.console;

import presentation.UiManager;

import java.util.*;

public class ConsoleUiManager implements UiManager {
	private final Scanner scanner = new Scanner(System.in);

	@Override
	public void start() {}

	@Override
	public HashMap<String, String> logIn() {
		HashMap<String, String> credentials = new HashMap<>();
		System.out.println("INICI DE SESSIÓ");
		credentials.put("identificador", askString("Identificador: "));
		credentials.put("password", askString("Contrasenya: "));
		return credentials;
	}

	@Override
	public void wrongCredentials(String message) {
		System.out.printf("\nError! %s\n\n", message);
	}

	private String askString(String message) {
		String input;
		do {
			System.out.print(message);
			input = scanner.nextLine();
		} while (input.isEmpty());
		return input;
	}
}
