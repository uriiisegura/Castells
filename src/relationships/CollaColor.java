package relationships;

import java.awt.*;
import java.time.LocalDate;

public class CollaColor extends Periode {
	private final Color color;

	public CollaColor(Color color, LocalDate desDe, LocalDate finsA) {
		super(desDe, finsA);
		this.color = color;
	}
}
