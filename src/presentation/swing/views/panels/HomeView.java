package presentation.swing.views.panels;

import presentation.swing.custom.JTitle;

import java.awt.*;

public class HomeView extends ClearableJPanel {
	public HomeView() {
		configureLayout();
	}

	private void configureLayout() {
		setLayout(new BorderLayout());
		add(new JTitle("SiGAC - CCCC"), BorderLayout.NORTH);
	}
}
