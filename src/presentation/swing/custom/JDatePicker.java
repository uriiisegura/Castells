package presentation.swing.custom;

import com.toedter.calendar.JDateChooser;

import java.util.Date;

public class JDatePicker extends JDateChooser {
	public JDatePicker() {
		this(null);
	}

	public JDatePicker(Date defaultDate) {
		setDateFormatString("dd/MM/yyyy");
		setDate(defaultDate);
	}

	public void clear() {
		setDate(null);
	}
}
