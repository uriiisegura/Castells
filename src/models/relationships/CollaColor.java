package models.relationships;

import models.Periode;

import java.awt.*;
import java.time.LocalDate;

public class CollaColor extends Periode {
	private final Color color;

	public CollaColor(Color color, LocalDate desDe, LocalDate finsA) {
		super(desDe, finsA);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public String getHexColor() {
		return "#" + Integer.toHexString(color.getRGB()).substring(2);
	}
}
