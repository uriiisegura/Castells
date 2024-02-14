package presentation.menus;

import presentation.swing.views.MainView;
import presentation.swing.views.panels.ClearableJPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MenuController implements ActionListener {
	private final HashMap<JMenuItem, ClearableJPanel> itemPanelMap = new HashMap<>();
	private final MainView mainView;

	public MenuController(MainView mainView) {
		this.mainView = mainView;
	}

	public void addMenuItem(JMenuItem item, ClearableJPanel panel) {
		itemPanelMap.put(item, panel);
		item.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem) e.getSource();
		ClearableJPanel panel = itemPanelMap.get(item);
		if (panel == null)
			return;
		mainView.setView(panel);
	}
}
