package presentation.console;

import business.dto.*;
import presentation.options.MenuOption;

import java.util.*;

public class ConsoleUiManager {
	private final Scanner scanner = new Scanner(System.in);

	public void start() {}

	public LogInDTO logIn() {
		System.out.println("INICI DE SESSIÓ");
		String identifier = askString("Identificador: ");
		String password = askString("Contrasenya: ");
		return new LogInDTO(identifier, password);
	}

	public void showError(String message) {
		System.out.printf("\nError! %s\n\n", message);
	}

	public MenuOption askMenuOption(MenuOption[] options) {
		System.out.println("\nOpcions:");
		for (int i = 0; i < options.length; i++)
			System.out.printf("%d) %s\n", i+1, options[i].text());
		return options[askIntInRange("Opció: ", 1, options.length) - 1];
	}

	public CastellerDTO askNewCasteller() {
		System.out.println("\nAFEGEIX UN NOU CASTELLER A LA BASE DE DADES");
		String dni = askString("\tDNI / NIE: ");
		String nom = askString("\tNom: ");
		String cognom1 = askString("\tPrimer cognom: ");
		System.out.print("\tSegon cognom (deixar en blanc si no en té): ");
		String cognom2 = scanner.nextLine();
		String sexe = askString("\tSexe (home / dona / no binari): ");
		String dataNaixement = askString("\tData de naixement (yyyy-mm-dd): ");
		System.out.print("\tData de defunció (yyyy-mm-dd) (deixar en blanc si és viu): ");
		String dataDefuncio = scanner.nextLine();
		return new CastellerDTO(dni, nom, cognom1, cognom2, sexe, dataNaixement, dataDefuncio);
	}

	public void showMessage(String message) {
		System.out.printf("\n%s\n", message);
	}

	public boolean askBoolean(String message) {
		String input;
		do {
			System.out.print(message);
			input = scanner.nextLine().toLowerCase();
		} while (!input.equals("s") && !input.equals("n"));
		return input.equals("s");
	}

	public int askOptionFromList(String title, List<String> options, String message) {
		System.out.println(title);
		for (int i = 0; i < options.size(); i++)
			System.out.printf("%d) %s\n", i+1, options.get(i));
		return askIntInRange(message, 1, options.size()) - 1;
	}

	public EsDeLaCollaDTO askEsDeLaColla() {
		System.out.println("\nAFEGEIX UN CASTELLER A UNA COLLA");
		String desDe = askString("\tData d'entrada a la colla (yyyy-mm-dd): ");
		System.out.print("\tData de sortida de la colla (yyyy-mm-dd) (deixar en blanc si no n'ha sortit): ");
		String finsA = scanner.nextLine();
		String malnom = askString("\tMalnom: ");
		return new EsDeLaCollaDTO(desDe, finsA, malnom);
	}

	public CollaDTO askNewColla() {
		System.out.println("\nAFEGEIX UNA NOVA COLLA A LA BASE DE DADES");
		String id = askString("\tId: ");
		boolean universitaria = askBoolean("\tÉs universitària? (s/n): ");
		return new CollaDTO(id, universitaria);
	}

	public CollaNomDTO askCollaNom() {
		System.out.println("\nAFEGEIX UN NOM A LA COLLA");
		String nom = askString("\tNom: ");
		String desDe = askString("\tDes de (yyyy-mm-dd): ");
		System.out.print("\tFins a (yyyy-mm-dd) (deixar en blanc si és el nom actual): ");
		String finsA = scanner.nextLine();
		return new CollaNomDTO(nom, desDe, finsA);
	}

	public PeriodeDTO askCollaFundacio() {
		System.out.println("\nAFEGEIX UNA FUNDACIÓ DE LA COLLA");
		String desDe = askString("\tDes de (yyyy-mm-dd): ");
		System.out.print("\tFins a (yyyy-mm-dd) (deixar en blanc si la colla no s'ha dissolt): ");
		String finsA = scanner.nextLine();
		return new PeriodeDTO(desDe, finsA);
	}

	public CollaColorDTO askCollaColor() {
		System.out.println("\nAFEGEIX UN COLOR A LA COLLA");
		String color = askString("\tColor: ");
		String desDe = askString("\tDes de (yyyy-mm-dd): ");
		System.out.print("\tFins a (yyyy-mm-dd) (deixar en blanc si és el color actual): ");
		String finsA = scanner.nextLine();
		return new CollaColorDTO(desDe, finsA, color);
	}

	public CollaAdrecaDTO askCollaAdreca() {
		System.out.println("\nAFEGEIX UNA ADREÇA A LA COLLA");
		String adreca = askString("\tAdreça: ");
		String desDe = askString("\tDes de (yyyy-mm-dd): ");
		System.out.print("\tFins a (yyyy-mm-dd) (deixar en blanc si és l'adreça actual): ");
		String finsA = scanner.nextLine();
		return new CollaAdrecaDTO(adreca, desDe, finsA);
	}

	public CiutatDTO askNewCiutat() {
		System.out.println("\nAFEGEIX UNA NOVA CIUTAT A LA BASE DE DADES");
		String nom = askString("\tNom: ");
		return new CiutatDTO(nom);
	}

	private String askString(String message) {
		String input;
		do {
			System.out.print(message);
			input = scanner.nextLine();
		} while (input.isEmpty());
		return input;
	}

	private int askInt(String message) {
		int input;
		do {
			System.out.print(message);
			try {
				input = Integer.parseInt(scanner.nextLine());
				return input;
			} catch (NumberFormatException ignored) {}
		} while (true);
	}

	private int askIntInRange(String message, int min, int max) {
		int input;
		do {
			input = askInt(message);
		} while (input < min || input > max);
		return input;
	}
}
