package relationships;

import models.Carrec;
import models.Casteller;
import models.Colla;
import models.Periode;

import java.time.LocalDate;

public class TeCarrec extends Periode {
	private Casteller casteller;
	private Colla colla;
	private Carrec carrec;

	public TeCarrec(Casteller casteller, Colla colla, Carrec carrec, LocalDate desDe, LocalDate finsA) {
		super(desDe, finsA);
		this.casteller = casteller;
		this.colla = colla;
		this.carrec = carrec;
	}
}
