package presentation.swing.views;

import business.dto.LogInDTO;
import presentation.swing.CustomView;
import presentation.swing.SwingController;
import presentation.swing.controllers.LogInController;
import presentation.swing.custom.JPasswordFieldWithPlaceholder;
import presentation.swing.custom.JTextFieldWithPlaceholder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;

public class LogInView extends JFrame implements CustomView {
	private final SwingController controller;
	private final JTextFieldWithPlaceholder dniField = new JTextFieldWithPlaceholder("DNI / NIE");
	private final JPasswordFieldWithPlaceholder passwordField = new JPasswordFieldWithPlaceholder("Contrasenya");
	private final JCheckBox saveLogInInfo = new JCheckBox("Recorda'm");
	private final JButton logInButton = new JButton("Inicia sessió");

	public LogInView(SwingController controller) {
		this.controller = controller;
		configureWindow();
		configureLayout();
	}

	@Override
	public void start() {
		setVisible(true);
		requestFocus();
	}

	@Override
	public void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void setDefaultLogInInfo(LogInDTO logIn) {
		if (logIn == null)
			return;
		dniField.setDefaultValue(logIn.getIdentifier());
		passwordField.setDefaultValue(logIn.getPassword());
		saveLogInInfo.setSelected(true);
	}

	public void addKeyAdapters(KeyAdapter keyAdapter) {
		addKeyListener(keyAdapter);
		dniField.addKeyListener(keyAdapter);
		passwordField.addKeyListener(keyAdapter);
	}

	public void logIn() {
		controller.logIn(new LogInDTO(dniField.getText(), passwordField.getInput()), saveLogInInfo.isSelected());
	}

	private void configureWindow() {
		setTitle("SiGAC - Inici de sessió");
		setSize(300, 150);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void configureLayout() {
		setLayout(new GridLayout(4, 1));
		add(dniField);
		add(passwordField);
		add(saveLogInInfo);
		add(logInButton);
		logInButton.addActionListener(new LogInController(this));
	}
}
