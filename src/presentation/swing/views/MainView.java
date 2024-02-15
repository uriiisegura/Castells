package presentation.swing.views;

import business.dto.CastellerDTO;
import business.dto.EsDeLaCollaDTO;
import presentation.menus.CreateDataMenuOptions;
import presentation.menus.EditDataMenuOptions;
import presentation.menus.MenuController;
import presentation.swing.CustomView;
import presentation.swing.SwingController;
import presentation.swing.views.panels.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

public class MainView extends JFrame implements CustomView {
	private final SwingController controller;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu home = new JMenu("Inici");
	private final JMenu createMenu = new JMenu("Afegeix");
	private final JMenuItem createCasteller = new JMenuItem(CreateDataMenuOptions.CREATE_CASTELLER.text());
	private final JMenuItem createColla = new JMenuItem(CreateDataMenuOptions.CREATE_COLLA.text());
	private final JMenu editMenu = new JMenu("Edita");
	private final JMenuItem editCasteller = new JMenuItem(EditDataMenuOptions.EDIT_CASTELLER.text());
	private final JMenuItem editColla = new JMenuItem(EditDataMenuOptions.EDIT_COLLA.text());
	private final JMenuItem addCastellerToColla = new JMenuItem(EditDataMenuOptions.ADD_CASTELLER_TO_COLLA.text());
	private final JMenu viewMenu = new JMenu("Consulta");
	private final JMenu deleteMenu = new JMenu("Elimina");
	private final JMenu surt = new JMenu("Surt");
	private final MenuController menuController = new MenuController(this);
	private final HomeView homeView = new HomeView();
	private final CreateCastellerView createCastellerView = new CreateCastellerView(this);
	private final CreateCollaView createCollaView = new CreateCollaView();
	private final AddCastellerToCollaView addCastellerToCollaView = new AddCastellerToCollaView(this);

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

	public void goHome() {
		setView(homeView);
	}

	public void createCasteller(CastellerDTO castellerDTO) {
		controller.createCasteller(castellerDTO);
	}

	public boolean askBoolean(String message) {
		return JOptionPane.showConfirmDialog(this, message, "Confirmació", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	public void addCastellerToColla(CastellerDTO castellerDTO, HashMap<String, String> colles) {
		setView(addCastellerToCollaView);
		addCastellerToCollaView.setCasteller(castellerDTO);
		addCastellerToCollaView.setColles(colles);
	}

	public void addCastellerToColla(EsDeLaCollaDTO esDeLaColla, String casteller, String colla) {
		controller.addCastellerToColla(esDeLaColla, casteller, colla);
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
		editMenu.add(editCasteller);
		editMenu.add(editColla);
		editMenu.add(addCastellerToColla);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(deleteMenu);
		menuBar.add(surt);
		setJMenuBar(menuBar);

		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				goHome();
			}
		});
		menuController.addMenuItem(home, homeView);
		menuController.addMenuItem(createCasteller, createCastellerView);
		menuController.addMenuItem(createColla, createCollaView);
		menuController.addMenuItem(addCastellerToColla, addCastellerToCollaView);
		surt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logOut();
			}
		});

		goHome();
	}

	private void logOut() {
		controller.logOut();
	}
}
