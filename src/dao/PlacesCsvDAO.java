package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import models.Ciutat;
import models.Placa;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlacesCsvDAO {
	private final static String path = "data/places.csv";

	public List<Placa> load(List<Ciutat> ciutats) throws IOException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));
		List<Placa> places = new ArrayList<>();

		String[] line;
		csvReader.skip(1);
		while ((line = csvReader.readNext()) != null) {
			String[] dataCsv = line;
			Ciutat ciutat = ciutats.stream().filter(c -> c.getId().equals(dataCsv[2])).toList().get(0);
			Placa placa = new Placa(
					dataCsv[0],
					dataCsv[1],
					ciutat,
					Double.parseDouble(dataCsv[3]),
					Double.parseDouble(dataCsv[4])
			);

			ciutat.addPlaca(placa);
			places.add(placa);
		}

		return places;
	}
}
