package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import config.Config;
import models.Casteller;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CastellerCsvDAO {
	private final static String path = "data/castellers.csv";

	public List<Casteller> load() throws IOException, ParseException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));
		List<Casteller> castellers = new ArrayList<>();

		String[] dataCsv;
		csvReader.skip(1);
		while ((dataCsv = csvReader.readNext()) != null) {
			Casteller casteller = new Casteller(
					dataCsv[0],
					dataCsv[1],
					dataCsv[2],
					dataCsv[3],
					Config.parseDate(dataCsv[4]),
					Config.parseDate(dataCsv[5])
			);

			castellers.add(casteller);
		}

		return castellers;
	}
}
