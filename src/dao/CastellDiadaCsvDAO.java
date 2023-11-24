package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import enums.ResultatsT;
import models.*;
import relationships.CastellDiada;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CastellDiadaCsvDAO {
	private final static String path = "data/castells_diades.csv";

	public List<CastellDiada> load(List<Colla> colles, List<Diada> diades, List<Castell> castells) throws IOException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));
		List<CastellDiada> castellsFets = new ArrayList<>();

		String[] line;
		csvReader.skip(1);
		while ((line = csvReader.readNext()) != null) {
			String[] dataCsv = line;
			Colla colla = colles.stream().filter(c -> c.getId().equals(dataCsv[1])).toList().get(0);
			Diada diada = diades.stream().filter(d -> d.getId() == Integer.parseInt(dataCsv[2])).toList().get(0);
			Castell castell = castells.stream()
					.filter(c -> (colla instanceof CollaUniversitaria) == (c instanceof CastellUniversitari))
					.filter(c -> c.toString().equals(dataCsv[3])).toList().get(0);
			CastellDiada castellFet = new CastellDiada(
					Long.parseLong(dataCsv[0]),
					colla,
					diada,
					castell,
					ResultatsT.valueOf(dataCsv[4]),
					Integer.parseInt(dataCsv[5])
			);

			colla.addCastell(castellFet);
			diada.addCastell(castellFet);
			castell.addRealitzacio(castellFet);
			castellsFets.add(castellFet);
		}

		return castellsFets;
	}
}
