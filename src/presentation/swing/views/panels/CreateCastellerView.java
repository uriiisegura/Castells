package presentation.swing.views.panels;

import presentation.swing.controllers.CreateCastellerController;
import presentation.swing.custom.*;
import presentation.swing.views.MainView;

import javax.swing.*;
import java.awt.*;

public class CreateCastellerView extends ClearableJPanel {
	private final MainView mainView;
	private final JCastellerForms createCastellerForm = new JCastellerForms();
	private final JButton submitButton = new JButton("Crea");

	public CreateCastellerView(MainView mainView) {
		this.mainView = mainView;
		configureLayout();
	}

	@Override
	public void clear() {
		createCastellerForm.clear();
	}

	public void createCasteller() {
		mainView.createCasteller(createCastellerForm.get());
	}

	private void configureLayout() {
		setLayout(new BorderLayout());
		add(new JTitle("Crea un casteller"), BorderLayout.NORTH);
		add(createCastellerForm, BorderLayout.CENTER);
		add(submitButton, BorderLayout.SOUTH);
		submitButton.addActionListener(new CreateCastellerController(this));
	}
}
