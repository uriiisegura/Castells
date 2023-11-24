package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import models.Carrec;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CarrecCsvDAO {
	private final static String path_tecnica = "data/carrecs_tecnica.csv";
	private final static String path_junta = "data/carrecs_junta.csv";

	public List<Carrec> load() throws IOException, ParseException, CsvValidationException {
		List<Carrec> carrecs = new ArrayList<>();

		loadCarrecs(path_tecnica, carrecs);
		loadCarrecs(path_junta, carrecs);

		return carrecs;
	}

	private void loadCarrecs(String path, List<Carrec> carrecs) throws IOException, ParseException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));

		String[] dataCsv;
		csvReader.skip(1);
		while ((dataCsv = csvReader.readNext()) != null) {
			carrecs.add(new Carrec(
					dataCsv[0],
					dataCsv[1]
			));
		}
	}
}
