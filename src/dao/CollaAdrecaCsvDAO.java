package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import config.Config;
import models.Ciutat;
import models.Colla;
import relationships.CollaAdreca;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class CollaAdrecaCsvDAO {
	private final static String path = "data/colla_adreces.csv";

	public void load(List<Colla> colles, List<Ciutat> ciutats) throws IOException, ParseException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));

		String[] line;
		csvReader.skip(1);
		while ((line = csvReader.readNext()) != null) {
			String[] dataCsv = line;
			colles.stream().filter(c -> c.getId().equals(dataCsv[0])).toList().get(0).addAdreca(new CollaAdreca(
					dataCsv[1],
					ciutats.stream().filter(c -> c.getNom().equals(dataCsv[2])).toList().get(0),
					Config.parseDate(dataCsv[3]),
					Config.parseDate(dataCsv[4])
			));
		}
	}
}
