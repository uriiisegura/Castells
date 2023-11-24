import com.opencsv.exceptions.CsvValidationException;
import dao.*;
import models.Carrec;
import models.Castell;
import models.Casteller;
import models.Colla;
import relationships.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, CsvValidationException {
        List<Castell> castellsConvencionals = new CastellsConvencionalsCsvDAO().load();
        List<Castell> castellsUniversitaris = new CastellsUniversitarisCsvDAO().load();
        List<EstaPuntuat> puntuacions = new EstaPuntuatCsvDAO().load(castellsConvencionals, castellsUniversitaris);
        List<Colla> colles = loadColles();
        List<Casteller> castellers = loadCastellers(colles);
        List<Carrec> carrecs = loadCarrecs(castellers, colles);
    }

    private static List<Colla> loadColles() throws IOException, CsvValidationException, ParseException {
        List<Colla> colles = new CollaCsvDAO().load();
        new CollaNomCsvDAO().load(colles);
        new CollaFundacioCsvDAO().load(colles);
        new CollaColorCsvDAO().load(colles);
        new CollaAdrecaCsvDAO().load(colles);

        return colles;
    }

    private static List<Casteller> loadCastellers(List<Colla> colles) throws IOException, CsvValidationException, ParseException {
        List<Casteller> castellers = new CastellerCsvDAO().load();
        new EsDeLaCollaCsvDAO().load(castellers, colles);

        return castellers;
    }

    private static List<Carrec> loadCarrecs(List<Casteller> castellers, List<Colla> colles) throws IOException, CsvValidationException, ParseException {
        List<Carrec> carrecs = new CarrecCsvDAO().load();
        new TeCarrecCsvDAO().load(castellers, colles, carrecs);

        return carrecs;
    }
}
