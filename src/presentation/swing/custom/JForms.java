package presentation.swing.custom;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class JForms extends JPanel {
	private final List<String> labels = new ArrayList<>();
	private final List<JComponent> components = new ArrayList<>();

	public JForms() {
		configureLayout();
	}

	private void configureLayout() {
		setLayout(new GridBagLayout());
	}

	public void addField(String label, JComponent component) {
		labels.add(label);
		components.add(component);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = labels.size() - 1;
		constraints.weightx = 0.5;
		constraints.insets = new Insets(5, 5, 5, 5);
		add(new JLabel(label), constraints);
		constraints.gridx = 1;
		constraints.weightx = 1;
		add(component, constraints);
	}

	public void clear() {}

	public Object get() {
		return null;
	}
}
