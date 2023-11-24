package relationships;

import models.Carrec;
import models.Casteller;
import models.Colla;

import java.time.LocalDate;

public class TeCarrec extends Periode {
	private final Casteller casteller;
	private final Colla colla;
	private final Carrec carrec;

	public TeCarrec(Casteller casteller, Colla colla, Carrec carrec, LocalDate desDe, LocalDate finsA) {
		super(desDe, finsA);
		this.casteller = casteller;
		this.colla = colla;
		this.carrec = carrec;
	}
}
