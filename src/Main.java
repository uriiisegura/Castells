import config.SqlConnection;
import dao.*;
import models.*;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final SqlConnection connection = new SqlConnection();

    private static List<Ciutat> ciutats;
    private static List<Casteller> castellers;
    private static List<Colla> colles;
    private static List<Carrec> carrecs;
    private static List<Estructura> estructures;
    private static List<Pisos> pisos;
    private static List<Reforcos> reforcos;
    private static List<Rengla> rengles;
    private static List<Castell> castells;

    public static void main(String[] args)  {
        loadCiutats();
        loadCastellers();
        loadColles();
        loadCarrecs();
        loadCastells();

        try {
            connection.connection.close();
        } catch (SQLException ignored) {}
    }

    private static void loadCiutats() {
        ciutats = new CiutatSqlDAO(connection.connection).loadAll();
    }

    private static void loadCastellers() {
        castellers = new CastellerSqlDAO(connection.connection).loadAll();
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
}
