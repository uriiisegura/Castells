package presentation.options;

public enum AddDataMenuOptions implements MenuOption {
	ADD_CASTELLER("Afegir un nou casteller"),
	CREATE_COLLA("Crear una nova colla");

	private final String text;

	AddDataMenuOptions(String text) {
		this.text = text;
	}

	@Override
	public String text() {
		return text;
	}
}
