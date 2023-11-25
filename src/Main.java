import com.opencsv.exceptions.CsvValidationException;
import dao.*;
import enums.RenglesT;
import models.*;
import org.apache.commons.lang3.tuple.Pair;
import relationships.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, CsvValidationException {
        List<Ciutat> ciutats = new CiutatsCsvDAO().load();
        List<Placa> places = new PlacesCsvDAO().load(ciutats);
        List<Castell> castellsUniversitaris = new CastellsUniversitarisCsvDAO().load();
        List<Castell> castellsConvencionals = new CastellsConvencionalsCsvDAO().load();
        List<Castell> castells = Stream.concat(castellsUniversitaris.stream(), castellsConvencionals.stream()).toList();
        List<EstaPuntuat> puntuacions = new EstaPuntuatCsvDAO().load(castellsUniversitaris, castellsConvencionals);
        List<Colla> colles = loadColles(ciutats);
        List<Casteller> castellers = loadCastellers(colles);
        List<Carrec> carrecs = loadCarrecs(castellers, colles);
        List<Diada> diades = new DiadaCsvDAO().load(places);
        List<CastellDiada> castellsFets = new CastellDiadaCsvDAO().load(colles, diades, castells);
        Map<Pair<CastellDiada, RenglesT>, CastellLineUp> renglaLineUps = new RenglaLineUpCsvDAO().load(castellsFets, castellers);
    }

    private static List<Colla> loadColles(List<Ciutat> ciutats) throws IOException, CsvValidationException, ParseException {
        List<Colla> colles = new CollaCsvDAO().load();
        new CollaNomCsvDAO().load(colles);
        new CollaFundacioCsvDAO().load(colles);
        new CollaColorCsvDAO().load(colles);
        new CollaAdrecaCsvDAO().load(colles, ciutats);

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
