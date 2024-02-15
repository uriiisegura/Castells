package presentation.swing.custom;

import business.dto.CastellerDTO;
import business.dto.EsDeLaCollaDTO;
import config.DateParser;

import java.util.HashMap;

public class JCastellerToCollaForms extends JForms {
	private final JSelector castellerSelector = new JSelector();
	private final JSelector collaSelector = new JSelector();
	private final JTextFieldWithPlaceholder malnomField = new JTextFieldWithPlaceholder("Malnom");
	private final JDatePicker dataIniciPicker = new JDatePicker();
	private final JDatePicker dataFiPicker = new JDatePicker();

	public JCastellerToCollaForms() {
		configureLayout();
	}

	@Override
	public void clear() {
		castellerSelector.clear();
		collaSelector.clear();
		malnomField.clear();
		dataIniciPicker.clear();
		dataFiPicker.clear();
	}

	@Override
	public EsDeLaCollaDTO get() {
		return new EsDeLaCollaDTO(
				DateParser.parseLocalDate(dataIniciPicker.getDate()),
				DateParser.parseLocalDate(dataFiPicker.getDate()),
				malnomField.getInput()
		);
	}

	public String getCasteller() {
		return castellerSelector.get();
	}

	public String getColla() {
		return collaSelector.get();
	}

	public void setCasteller(CastellerDTO casteller) {
		castellerSelector.clear();
		castellerSelector.addItem(casteller.getDni(), String.format("[%s] %s", casteller.getDni(), casteller.getFullName()));
		castellerSelector.setEnabled(false);
	}

	public void setColles(HashMap<String, String> colles) {
		collaSelector.clear();
		colles.forEach(collaSelector::addItem);
	}

	private void configureLayout() {
		addField("Casteller", castellerSelector);
		addField("Colla", collaSelector);
		addField("Data d'entrada", dataIniciPicker);
		addField("Data de sortida", dataFiPicker);
	}
}
