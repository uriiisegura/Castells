package models;

import java.time.ZonedDateTime;

public class RegistreEspatlla extends Registre {
	private float espatlla;

	public RegistreEspatlla(String numeroDeRegistre, ZonedDateTime dataHora, Casteller casteller, float espatlla) {
		super(numeroDeRegistre, dataHora, casteller);
		this.espatlla = espatlla;
	}
}
