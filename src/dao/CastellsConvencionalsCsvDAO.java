package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import enums.EstructuresT;
import enums.PisosT;
import enums.ReforcosT;
import models.Castell;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CastellsConvencionalsCsvDAO {
	private final static String path = "data/castells_convencionals.csv";

	public List<Castell> load() throws IOException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));
		List<Castell> castells = new ArrayList<>();

		String[] dataCsv;
		csvReader.skip(1);
		while ((dataCsv = csvReader.readNext()) != null) {
			Castell castell = new Castell(
					EstructuresT.valueOf(dataCsv[0]),
					PisosT.valueOf(dataCsv[1]),
					ReforcosT.valueOf(dataCsv[2]),
					Integer.parseInt(dataCsv[3]),
					Boolean.parseBoolean(dataCsv[4]),
					Integer.parseInt(dataCsv[5])
			);

			castells.add(castell);
		}

		return castells;
	}
}
