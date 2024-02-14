package presentation.swing.custom;

import presentation.swing.managers.FontManager;

import javax.swing.*;

public class JTitle extends JLabel {
	public JTitle(String text) {
		super(text);
		setHorizontalAlignment(JLabel.CENTER);
		setFont(FontManager.TITLE);
	}
}
