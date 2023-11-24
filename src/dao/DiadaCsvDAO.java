package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import config.Config;
import models.Diada;
import models.Placa;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiadaCsvDAO {
	private final static String path = "data/diades.csv";

	public List<Diada> load(List<Placa> places) throws IOException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));
		List<Diada> diades = new ArrayList<>();

		String[] line;
		csvReader.skip(1);
		while ((line = csvReader.readNext()) != null) {
			String[] dataCsv = line;
			Placa placa = places.stream().filter(p -> p.getId().equals(dataCsv[3])).toList().get(0);
			Diada diada = new Diada(
					Integer.parseInt(dataCsv[0]),
					dataCsv[1],
					Config.parseDatetime(dataCsv[2]),
					placa
			);

			placa.addDiada(diada);
			diades.add(diada);
		}

		return diades;
	}
}
