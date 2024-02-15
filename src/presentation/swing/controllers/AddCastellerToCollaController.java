package presentation.swing.controllers;

import presentation.swing.views.panels.AddCastellerToCollaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCastellerToCollaController implements ActionListener {
	private final AddCastellerToCollaView view;

	public AddCastellerToCollaController(AddCastellerToCollaView createCastellerView) {
		this.view = createCastellerView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.addCastellerToColla();
	}
}
