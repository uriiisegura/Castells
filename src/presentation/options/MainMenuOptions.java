package presentation.options;

public enum MainMenuOptions implements MenuOption {
	ADD_DATA("Afegir dades"),
	EDIT_DATA("Editar dades"),
	VIEW_DATA("Consultar dades"),
	EXIT("Sortir");

	private final String text;

	MainMenuOptions(String text) {
		this.text = text;
	}

	@Override
	public String text() {
		return text;
	}
}
