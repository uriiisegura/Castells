package presentation.swing.custom;

import javax.swing.*;

public class JSelector extends JComboBox<JSelector.Item> {
	public void addItem(String id, String text) {
		addItem(new Item(id, text));
	}

	public void clear() {
		removeAllItems();
		setEnabled(true);
	}

	public String get() {
		return getItem().getId();
	}

	private Item getItem() {
		return (Item) getSelectedItem();
	}

	static class Item {
		private final String id;
		private final String text;

		public Item(String id, String text) {
			this.id = id;
			this.text = text;
		}

		public String getId() {
			return id;
		}

		public String getText() {
			return text;
		}

		@Override
		public String toString() {
			return text;
		}
	}
}
