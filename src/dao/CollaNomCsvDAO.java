package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import config.Config;
import models.Colla;
import relationships.CollaNom;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class CollaNomCsvDAO {
	private final static String path = "data/colla_noms.csv";

	public void load(List<Colla> colles) throws IOException, ParseException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));

		String[] line;
		csvReader.skip(1);
		while ((line = csvReader.readNext()) != null) {
			String[] dataCsv = line;
			colles.stream().filter(c -> c.getId().equals(dataCsv[0])).toList().get(0).addNom(new CollaNom(
					dataCsv[1],
					Config.parseDate(dataCsv[2]),
					Config.parseDate(dataCsv[3])
			));
		}
	}
}
