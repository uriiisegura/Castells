package presentation.console;

import models.diades.CastellLineUp;
import models.diades.RenglaLineUp;
import presentation.UiManager;
import relationships.CastellDiada;

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

	@Override
	public void showCastells(List<CastellDiada> castells) {
		System.out.println("\nCastells:");
		for (CastellDiada cd : castells) {
			System.out.printf("- %s %s (%s) - %s (%s)\n", cd.getNotacio(), cd.getResultat().text(), cd.getDate(), cd.getDiadaNom(), cd.getFullLocation());
			for (CastellLineUp lu : cd.getLineUps()) {
				if (lu instanceof RenglaLineUp rlu)
					System.out.printf("\t- %s: %s\n", rlu.getRenglaNom(), String.join(", ", rlu.getMalnomsAt(cd.getColla(), cd.getDate())));
			}
		}
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
