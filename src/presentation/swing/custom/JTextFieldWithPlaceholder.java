package presentation.swing.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextFieldWithPlaceholder extends JTextField {
	private final String placeholder;
	private boolean wroteSomething = false;

	public JTextFieldWithPlaceholder(String placeholder) {
		this(placeholder, null);
	}

	public JTextFieldWithPlaceholder(String placeholder, String defaultValue) {
		this.placeholder = placeholder;
		if (defaultValue != null) {
			setDefaultValue(defaultValue);
		} else {
			clearDefaultValue();
		}
		addFocusListener(new PlaceholderListener());
	}

	public void setDefaultValue(String defaultValue) {
		setText(defaultValue);
		wroteSomething = true;
		setForeground(Color.BLACK);
	}

	public void clearDefaultValue() {
		setText(placeholder);
		wroteSomething = false;
		setForeground(Color.GRAY);
	}

	public String getInput() {
		return wroteSomething ? getText() : "";
	}

	public void clear() {
		setForeground(Color.GRAY);
		setText(placeholder);
		wroteSomething = false;
	}

	private class PlaceholderListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {
			if (getText().equals(placeholder) && !wroteSomething)
				setText("");
			setForeground(Color.BLACK);
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (getText().isEmpty()) {
				setText(placeholder);
				setForeground(Color.GRAY);
				wroteSomething = false;
			} else
				wroteSomething = true;
		}
	}
}
