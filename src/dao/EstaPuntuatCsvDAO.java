package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import config.Config;
import models.Castell;
import relationships.EstaPuntuat;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EstaPuntuatCsvDAO {
	private final static String path_convencionals = "data/puntuacio_convencionals.csv";
	private final static String path_universitaris = "data/puntuacio_universitaris.csv";

	public List<EstaPuntuat> load(List<Castell> castellsConvencionals, List<Castell> castellsUniversitaris) throws IOException, ParseException, CsvValidationException {
		List<EstaPuntuat> puntuacions = new ArrayList<>();

		loadPuntuacio(path_convencionals, castellsConvencionals, puntuacions);
		loadPuntuacio(path_universitaris, castellsUniversitaris, puntuacions);

		return puntuacions;
	}

	private void loadPuntuacio(String path, List<Castell> castells, List<EstaPuntuat> puntuacions) throws IOException, ParseException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));

		String[] line;
		csvReader.skip(1);
		while ((line = csvReader.readNext()) != null) {
			String[] dataCsv = line;
			Castell castell = castells.stream().filter(c -> c.toString().equals(dataCsv[0])).toList().get(0);
			EstaPuntuat puntuacio = new EstaPuntuat(
					castell,
					Config.parseDate(dataCsv[1]),
					Config.parseDate(dataCsv[2]),
					Integer.parseInt(dataCsv[3]),
					Integer.parseInt(dataCsv[4]),
					Integer.parseInt(dataCsv[5]),
					Integer.parseInt(dataCsv[6])
			);

			castell.addPuntuacio(puntuacio);
			puntuacions.add(puntuacio);
		}
	}
}
