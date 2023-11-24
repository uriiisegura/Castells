package models;

import relationships.CastellDiada;

import java.time.ZonedDateTime;
import java.util.Vector;

public class Diada {
	private int id;
	private String nom;
	private ZonedDateTime dataHora;
	private Placa placa;
	private Vector<CastellDiada> castells = new Vector<>();

	public Diada(int id, String nom, ZonedDateTime dataHora, Placa placa) {
		this.id = id;
		this.nom = nom;
		this.dataHora = dataHora;
		this.placa = placa;
	}

	public boolean addCastell(CastellDiada castell) {
		return castells.add(castell);
	}

	public int getId() {
		return id;
	}
}
