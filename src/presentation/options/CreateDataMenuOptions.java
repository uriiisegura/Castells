package presentation.options;

public enum CreateDataMenuOptions implements MenuOption {
	CREATE_CASTELLER("Crear un nou casteller"),
	CREATE_COLLA("Crear una nova colla");

	private final String text;

	CreateDataMenuOptions(String text) {
		this.text = text;
	}

	@Override
	public String text() {
		return text;
	}
}
