package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import models.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollaCsvDAO {
	private final static String path = "data/colles.csv";

	public List<Colla> load() throws IOException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));
		List<Colla> colles = new ArrayList<>();

		String[] dataCsv;
		csvReader.skip(1);
		while ((dataCsv = csvReader.readNext()) != null) {
			if (Boolean.parseBoolean(dataCsv[1]))
				colles.add(new CollaUniversitaria(dataCsv[0]));
			else
				colles.add(new CollaConvencional(dataCsv[0]));
		}

		return colles;
	}
}
