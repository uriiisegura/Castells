package presentation.options;

public enum AddDataMenuOptions implements MenuOption {
	ADD_CASTELLER("Afegir casteller");

	private final String text;

	AddDataMenuOptions(String text) {
		this.text = text;
	}

	@Override
	public String text() {
		return text;
	}
}
