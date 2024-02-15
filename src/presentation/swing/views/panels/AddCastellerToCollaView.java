package presentation.swing.views.panels;

import business.dto.CastellerDTO;
import presentation.swing.controllers.AddCastellerToCollaController;
import presentation.swing.custom.JCastellerToCollaForms;
import presentation.swing.custom.JTitle;
import presentation.swing.views.MainView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class AddCastellerToCollaView extends ClearableJPanel {
	private final MainView mainView;
	private final JCastellerToCollaForms castellerToCollaForms = new JCastellerToCollaForms();
	private final JButton submitButton = new JButton("Afegeix");

	public AddCastellerToCollaView(MainView mainView) {
		this.mainView = mainView;
		configureLayout();
	}

	@Override
	public void clear() {
		castellerToCollaForms.clear();
	}

	public void setCasteller(CastellerDTO casteller) {
		castellerToCollaForms.setCasteller(casteller);
	}

	public void setColles(HashMap<String, String> colles) {
		castellerToCollaForms.setColles(colles);
	}

	public void addCastellerToColla() {
		mainView.addCastellerToColla(castellerToCollaForms.get(), castellerToCollaForms.getCasteller(), castellerToCollaForms.getColla());
	}

	private void configureLayout() {
		setLayout(new BorderLayout());
		add(new JTitle("Afegeix un casteller a una colla"), BorderLayout.NORTH);
		add(castellerToCollaForms, BorderLayout.CENTER);
		add(submitButton, BorderLayout.SOUTH);
		submitButton.addActionListener(new AddCastellerToCollaController(this));
	}
}
