package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import enums.RenglesT;
import models.CastellLineUp;
import models.Casteller;
import models.RenglaLineUp;
import org.apache.commons.lang3.tuple.Pair;
import relationships.CastellDiada;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenglaLineUpCsvDAO {
	private final static String path = "data/rengles_lineup.csv";

	public Map<Pair<CastellDiada, RenglesT>, CastellLineUp> load(List<CastellDiada> castellsFets, List<Casteller> castellers) throws IOException, ParseException, CsvValidationException {
		CSVReader csvReader = new CSVReader(new FileReader(path));
		Map<Pair<CastellDiada, RenglesT>, CastellLineUp> lineUps = new HashMap<>();

		String[] line;
		csvReader.skip(1);
		while ((line = csvReader.readNext()) != null) {
			String[] dataCsv = line;
			CastellDiada castell = castellsFets.stream().filter(c -> c.getId() == Integer.parseInt(dataCsv[0])).toList().get(0);
			RenglesT rengla = RenglesT.valueOf(dataCsv[1]);
			Casteller casteller = castellers.stream().filter(c -> c.getDni().equals(dataCsv[2])).toList().get(0);
			Pair<CastellDiada, RenglesT> pair = Pair.of(castell, rengla);
			RenglaLineUp r;
			if (!lineUps.containsKey(pair)) {
				r = new RenglaLineUp(castell, rengla);
				lineUps.put(pair, r);
				castell.addLineUp(r);
			} else
				r = (RenglaLineUp) lineUps.get(pair);

			casteller.addLineUp(r);
			r.addCasteller(Integer.parseInt(dataCsv[3]), casteller);
		}

		return lineUps;
	}
}
