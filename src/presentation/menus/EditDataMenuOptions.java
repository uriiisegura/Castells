package presentation.menus;

public enum EditDataMenuOptions implements MenuOption {
	EDIT_CASTELLER("Edita un casteller"),
	EDIT_COLLA("Edita una colla"),
	ADD_CASTELLER_TO_COLLA("Afegeix un casteller a una colla");

	private final String text;

	EditDataMenuOptions(String text) {
		this.text = text;
	}

	@Override
	public String text() {
		return text;
	}
}
