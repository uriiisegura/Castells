package relationships;

import models.Casteller;
import models.Colla;

import java.time.LocalDate;

public class EsDeLaColla extends Periode {
	private final Casteller casteller;
	private final Colla colla;
	private final String malnom;

	public EsDeLaColla(Casteller casteller, Colla colla, String malnom, LocalDate desDe, LocalDate finsA) {
		super(desDe, finsA);
		this.casteller = casteller;
		this.colla = colla;
		this.malnom = malnom;
	}
}
