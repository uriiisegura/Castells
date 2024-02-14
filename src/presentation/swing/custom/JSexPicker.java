package presentation.swing.custom;

import javax.swing.*;

public class JSexPicker extends JComboBox<String> {
	public JSexPicker() {
		addItem("Home");
		addItem("Dona");
		addItem("No binari");
	}

	public String getSelected() {
		String selectedItem = (String) getSelectedItem();
		if (selectedItem == null)
			return null;
		return selectedItem.toLowerCase();
	}

	public void clear() {
		setSelectedIndex(0);
	}
}
