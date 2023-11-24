package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import models.Ciutat;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CiutatsCsvDAO {
	private final static String path = "data/ciutats.csv";

	public List<Ciutat> load() throws IOException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));
		List<Ciutat> ciutats = new ArrayList<>();

		String[] dataCsv;
		csvReader.skip(1);
		while ((dataCsv = csvReader.readNext()) != null) {
			ciutats.add(new Ciutat(
					dataCsv[0],
					dataCsv[1]
			));
		}

		return ciutats;
	}
}
