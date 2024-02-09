package models.castellers;

import java.time.ZonedDateTime;

public class RegistreComplet extends Registre {
	private float brac;
	private float espatlla;

	public RegistreComplet(String numeroDeRegistre, ZonedDateTime dataHora, Casteller casteller, float brac, float espatlla) {
		super(numeroDeRegistre, dataHora, casteller);
		this.brac = brac;
		this.espatlla = espatlla;
	}
}
