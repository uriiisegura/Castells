package presentation.swing.views;

import business.dto.CastellerDTO;
import presentation.menus.CreateDataMenuOptions;
import presentation.menus.MenuController;
import presentation.swing.CustomView;
import presentation.swing.SwingController;
import presentation.swing.views.panels.ClearableJPanel;
import presentation.swing.views.panels.CreateCastellerView;
import presentation.swing.views.panels.CreateCollaView;
import presentation.swing.views.panels.HomeView;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MainView extends JFrame implements CustomView {
	private final SwingController controller;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu home = new JMenu("Inici");
	private final JMenu createMenu = new JMenu("Afegeix");
	private final JMenuItem createCasteller = new JMenuItem(CreateDataMenuOptions.CREATE_CASTELLER.text());
	private final JMenuItem createColla = new JMenuItem(CreateDataMenuOptions.CREATE_COLLA.text());
	private final JMenu surt = new JMenu("Surt");
	private final MenuController menuController = new MenuController(this);
	private final HomeView homeView = new HomeView();
	private final CreateCastellerView createCastellerView = new CreateCastellerView(this);
	private final CreateCollaView createCollaView = new CreateCollaView();

	public MainView(SwingController controller) {
		this.controller = controller;
	}

	@Override
	public void start() {
		configureWindow();
		configureLayout();
		setVisible(true);
	}

	@Override
	public void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void setView(ClearableJPanel panel) {
		panel.clear();
		setContentPane(panel);
		revalidate();
		repaint();
	}

	public void createCasteller(CastellerDTO castellerDTO) {
		controller.createCasteller(castellerDTO);
	}

	private void configureWindow() {
		setTitle("SiGAC - Sistema de Gestió d'Arxius Castellers");
		setSize(800, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void configureLayout() {
		menuBar.add(home);
		createMenu.add(createCasteller);
		createMenu.add(createColla);
		menuBar.add(createMenu);
		menuBar.add(surt);
		setJMenuBar(menuBar);

		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setView(homeView);
			}
		});
		menuController.addMenuItem(home, homeView);
		menuController.addMenuItem(createCasteller, createCastellerView);
		menuController.addMenuItem(createColla, createCollaView);
		surt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logOut();
			}
		});

		setView(createCastellerView);
	}

	private void logOut() {
		controller.logOut();
	}
}
