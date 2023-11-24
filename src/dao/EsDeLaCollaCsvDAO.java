package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import config.Config;
import models.Casteller;
import models.Colla;
import relationships.EsDeLaColla;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class EsDeLaCollaCsvDAO {
	private final static String path = "data/castellers_colles.csv";

	public void load(List<Casteller> castellers, List<Colla> colles) throws IOException, ParseException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));

		String[] line;
		csvReader.skip(1);
		while ((line = csvReader.readNext()) != null) {
			String[] dataCsv = line;
			Casteller casteller = castellers.stream().filter(c -> c.getDni().equals(dataCsv[0])).toList().get(0);
			Colla colla = colles.stream().filter(c -> c.getId().equals(dataCsv[1])).toList().get(0);
			EsDeLaColla pertany = new EsDeLaColla(
					casteller,
					colla,
					dataCsv[2],
					Config.parseDate(dataCsv[3]),
					Config.parseDate(dataCsv[4])
			);

			casteller.addColla(pertany);
			colla.addCasteller(pertany);
		}
	}
}
