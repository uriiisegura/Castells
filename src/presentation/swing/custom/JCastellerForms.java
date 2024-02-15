package presentation.swing.custom;

import business.dto.CastellerDTO;
import config.DateParser;

public class JCastellerForms extends JForms {
	private final JTextFieldWithPlaceholder dniField = new JTextFieldWithPlaceholder("DNI / NIE");
	private final JTextFieldWithPlaceholder nomField = new JTextFieldWithPlaceholder("Nom");
	private final JTextFieldWithPlaceholder cognom1Field = new JTextFieldWithPlaceholder("Primer cognom");
	private final JTextFieldWithPlaceholder cognom2Field = new JTextFieldWithPlaceholder("Segon cognom");
	private final JSexPicker sexPicker = new JSexPicker();
	private final JDatePicker dataNaixementPicker = new JDatePicker();
	private final JDatePicker dataDefuncioPicker = new JDatePicker();

	public JCastellerForms() {
		configureLayout();
	}

	@Override
	public void clear() {
		dniField.clear();
		nomField.clear();
		cognom1Field.clear();
		cognom2Field.clear();
		sexPicker.clear();
		dataNaixementPicker.clear();
		dataDefuncioPicker.clear();
	}

	@Override
	public CastellerDTO get() {
		return new CastellerDTO(
				dniField.getInput(),
				nomField.getInput(),
				cognom1Field.getInput(),
				cognom2Field.getInput(),
				sexPicker.getSelected(),
				DateParser.parseLocalDate(dataNaixementPicker.getDate()),
				DateParser.parseLocalDate(dataDefuncioPicker.getDate())
		);
	}

	private void configureLayout() {
		addField("DNI / NIE", dniField);
		addField("Nom", nomField);
		addField("Primer cognom", cognom1Field);
		addField("Segon cognom", cognom2Field);
		addField("Sexe", sexPicker);
		addField("Data de naixement", dataNaixementPicker);
		addField("Data de defunció", dataDefuncioPicker);
	}
}
