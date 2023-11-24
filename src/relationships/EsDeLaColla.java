package relationships;

import models.Casteller;
import models.Colla;
import models.Periode;

import java.time.LocalDate;

public class EsDeLaColla extends Periode {
	private Casteller casteller;
	private Colla colla;
	private String malnom;

	public EsDeLaColla(Casteller casteller, Colla colla, String malnom, LocalDate desDe, LocalDate finsA) {
		super(desDe, finsA);
		this.casteller = casteller;
		this.colla = colla;
		this.malnom = malnom;
	}
}
