import config.SqlConnection;
import dao.sql.*;
import models.castellers.Casteller;
import models.castells.*;
import models.colles.Carrec;
import models.colles.Colla;
import models.diades.Diada;
import models.locations.Ciutat;
import models.locations.Location;
import relationships.CastellDiada;

import java.sql.SQLException;
import java.util.*;

public class Main {
    private static final SqlConnection connection = new SqlConnection();

    private static List<Ciutat> ciutats;
    private static List<Location> locations;
    private static List<Casteller> castellers;
    private static List<Colla> colles;
    private static List<Carrec> carrecs;
    private static List<Estructura> estructures;
    private static List<Pisos> pisos;
    private static List<Reforcos> reforcos;
    private static List<Rengla> rengles;
    private static List<Castell> castells;
    private static List<Diada> diades;
    private static List<CastellDiada> castellsFets;

    public static void main(String[] args)  {
        loadCiutats();
        loadCastellers();
        loadColles();
        loadCarrecs();
        loadCastells();
        loadDiades();

        for (Diada d : diades) {
            d.print();
        }

        try {
            connection.connection.close();
        } catch (SQLException ignored) {}
    }

    private static void loadCiutats() {
        ciutats = new CiutatSqlDAO(connection.connection).loadAll();
        locations = new LocationSqlDAO(connection.connection).loadAll(ciutats);
    }

    private static void loadCastellers() {
        castellers = new CastellerSqlDAO(connection.connection).loadAll();
        new RegistreSqlDAO(connection.connection).loadAll(castellers);
    }

    private static void loadColles() {
        colles = new CollaSqlDAO(connection.connection).loadAll();
        new EsDeLaCollaSqlDAO(connection.connection).loadAll(castellers, colles);
        new CollaColorSqlDAO(connection.connection).loadAll(colles);
        new CollaFundacioSqlDAO(connection.connection).loadAll(colles);
        new CollaNomSqlDAO(connection.connection).loadAll(colles);
        new CollaAdrecaSqlDAO(connection.connection).loadAll(colles, ciutats);
    }

    private static void loadCarrecs() {
        carrecs = new CarrecSqlDAO(connection.connection).loadAll();
        new TeCarrecSqlDAO(connection.connection).loadAll(castellers, colles, carrecs);
    }

    private static void loadCastells() {
        estructures = new EstructuraSqlDAO(connection.connection).loadAll();
        pisos = new PisosSqlDAO(connection.connection).loadAll();
        reforcos = new ReforcosSqlDAO(connection.connection).loadAll();
        rengles = new RenglaSqlDAO(connection.connection).loadAll(estructures);
        castells = new CastellSqlDAO(connection.connection).loadAll(estructures, pisos, reforcos);
    }

    private static void loadDiades() {
        diades = new DiadaSqlDAO(connection.connection).loadAll(locations);
        castellsFets = new CastellDiadaSqlDAO(connection.connection).loadAll(diades, castells, colles);
        new CastellLineUpSqlDAO(connection.connection).loadAll(castellers, castellsFets, rengles);
    }
}
