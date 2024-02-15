package presentation.menus;

public enum MainMenuOptions implements MenuOption {
	CREATE_DATA("Afegir-hi noves dades"),
	EDIT_DATA("Editar dades"),
	VIEW_DATA("Consultar dades"),
	DELETE_DATA("Eliminar dades"),
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
