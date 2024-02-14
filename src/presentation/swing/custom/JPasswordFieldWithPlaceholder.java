package presentation.swing.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JPasswordFieldWithPlaceholder extends JPasswordField {
	private final String placeholder;
	private boolean wroteSomething = false;

	public JPasswordFieldWithPlaceholder(String placeholder) {
		this(placeholder, null);
	}

	public JPasswordFieldWithPlaceholder(String placeholder, String defaultValue) {
		this.placeholder = placeholder;
		if (defaultValue != null)
			setDefaultValue(defaultValue);
		else
			clearDefaultValue();
		addFocusListener(new PlaceholderListener());
	}

	public void setDefaultValue(String defaultValue) {
		setText(defaultValue);
		wroteSomething = true;
		setForeground(Color.BLACK);
		hideChars();
	}

	public void clearDefaultValue() {
		setText(placeholder);
		wroteSomething = false;
		setForeground(Color.GRAY);
		showChars();
	}

	public String getInput() {
		return new String(getPassword());
	}

	public void clear() {
		setForeground(Color.GRAY);
		setText(placeholder);
		showChars();
		wroteSomething = false;
	}

	private void showChars() {
		setEchoChar((char) 0);
	}

	private void hideChars() {
		setEchoChar('•');
	}

	private class PlaceholderListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {
			if (getInput().equals(placeholder) && !wroteSomething)
				setText("");
			hideChars();
			setForeground(Color.BLACK);
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (getInput().isEmpty()) {
				setForeground(Color.GRAY);
				setText(placeholder);
				showChars();
				wroteSomething = false;
			} else
				wroteSomething = true;
		}
	}
}
