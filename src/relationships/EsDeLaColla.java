package relationships;

import models.castellers.Casteller;
import models.colles.Colla;
import models.Periode;

import java.time.LocalDate;

public class EsDeLaColla extends Periode {
	private Casteller casteller;
	private Colla colla;
	private String malnom;

	public EsDeLaColla(Casteller casteller, Colla colla, LocalDate desDe, LocalDate finsA, String malnom) {
		super(desDe, finsA);
		this.casteller = casteller;
		this.colla = colla;
		this.malnom = malnom;
	}
}
