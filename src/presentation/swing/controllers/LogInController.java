package presentation.swing.controllers;

import presentation.swing.views.LogInView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LogInController implements ActionListener {
	private final LogInView logInView;

	public LogInController(LogInView logInView) {
		this.logInView = logInView;
		logInView.addKeyAdapters(new KeyListener());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		submit();
	}

	private void submit() {
		logInView.logIn();
	}

	private class KeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				submit();
		}
	}
}
