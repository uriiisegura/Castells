package models;

import java.time.ZonedDateTime;

public class RegistreBrac extends Registre {
	private float brac;

	public RegistreBrac(String numeroDeRegistre, ZonedDateTime dataHora, Casteller casteller, float brac) {
		super(numeroDeRegistre, dataHora, casteller);
		this.brac = brac;
	}
}
