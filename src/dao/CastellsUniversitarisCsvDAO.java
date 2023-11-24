package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import enums.EstructuresT;
import enums.PisosT;
import enums.ReforcosT;
import models.Castell;
import models.CastellUniversitari;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;

public class CastellsUniversitarisCsvDAO {
    private final static String path = "data/castells_universitaris.csv";

    public List<Castell> load() throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(new FileReader(path));
        List<Castell> castells = new ArrayList<>();

        String[] dataCsv;
        csvReader.skip(1);
        while ((dataCsv = csvReader.readNext()) != null) {
            Castell castell = new CastellUniversitari(
                    EstructuresT.valueOf(dataCsv[0]),
                    PisosT.valueOf(dataCsv[1]),
                    ReforcosT.valueOf(dataCsv[2]),
                    Integer.parseInt(dataCsv[3]),
                    Boolean.parseBoolean(dataCsv[4]),
                    Integer.parseInt(dataCsv[5]),
                    Boolean.parseBoolean(dataCsv[6])
            );

            castells.add(castell);
        }

        return castells;
    }
}
