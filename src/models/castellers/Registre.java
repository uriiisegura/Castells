package models.castellers;

import java.time.ZonedDateTime;

public abstract class Registre {
	private final String numeroDeRegistre;
	private final ZonedDateTime dataHora;
	private final Casteller casteller;

	public Registre(String numeroDeRegistre, ZonedDateTime dataHora, Casteller casteller) {
		this.numeroDeRegistre = numeroDeRegistre;
		this.dataHora = dataHora;
		this.casteller = casteller;
	}

	public ZonedDateTime getDataHora() {
		return dataHora;
	}
}
