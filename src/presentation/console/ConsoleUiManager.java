package presentation.console;

import business.BusinessFacade;
import models.castellers.Casteller;
import models.colles.Colla;
import models.diades.CastellLineUp;
import models.diades.Diada;
import models.diades.RenglaLineUp;
import presentation.UiManager;
import relationships.CastellDiada;

import java.time.LocalDate;
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
		System.out.printf("Error! %s\n", message);
	}

	@Override
	public void showDiades(List<Diada> diades) {
		for (Diada diada : diades) {
			System.out.printf("- %s\n", diada.getNom());
			for (Map.Entry<Colla, Vector<CastellDiada>> hm : diada.getCastellsDictionary().entrySet()) {
				System.out.printf("\t- %s\n", hm.getKey().getCurrentNom());
				for (CastellDiada cd : hm.getValue()) {
					System.out.printf("\t\t- %s (%s)\n", cd.getNotacio(), cd.getResultat().text());
					for (CastellLineUp clu : cd.getLineUps()) {
						if (clu instanceof RenglaLineUp rlu) {
							System.out.printf("\t\t\t- %s\n", rlu.getRenglaNom());
							for (Casteller c : rlu.getCastellers())
								System.out.printf("\t\t\t\t- %s\n", c.getMalnomAtCollaAtTime(cd.getColla(), LocalDate.from(diada.getInici())));
						}
					}
				}
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
