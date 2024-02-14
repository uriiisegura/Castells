package presentation.swing.controllers;

import presentation.swing.views.panels.CreateCastellerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateCastellerController implements ActionListener {
	private final CreateCastellerView view;

	public CreateCastellerController(CreateCastellerView createCastellerView) {
		this.view = createCastellerView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.createCasteller();
	}
}
