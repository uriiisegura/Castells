package presentation.swing.views.panels;

import presentation.swing.custom.JTitle;

import java.awt.*;

public class CreateCollaView extends ClearableJPanel {
	public CreateCollaView() {
		configureLayout();
	}

	@Override
	public void clear() {
		// TODO
	}

	private void configureLayout() {
		setLayout(new BorderLayout());
		add(new JTitle("Crea una colla"), BorderLayout.NORTH);
	}
}
