package dao.sql;

import config.DateParser;
import dao.RegistreDAO;
import exceptions.SqlConnectionException;
import models.castellers.*;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.List;

public class RegistreSqlDAO implements RegistreDAO {
	private final static String tableName = "Registre";

	private final Connection connection;

	public RegistreSqlDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void loadAll(List<Casteller> castellers) {
		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String dni = resultSet.getString("casteller");
				Casteller casteller = castellers.stream().filter(c -> c.getDni().equals(dni)).findFirst().orElse(null);

				if (casteller == null) {
					// TODO:
					throw new SQLException("Casteller not found");
				}

				Registre registre;
				float alcadaEspatlla = resultSet.getFloat("alcadaEspatlla");
				if (resultSet.wasNull()) {
					registre = new RegistreBrac(
							resultSet.getString("numeroDeRegistre"),
							DateParser.parseZonedDateTime(resultSet.getTimestamp("dataHora")),
							casteller,
							resultSet.getFloat("alcadaBrac")
					);
				} else {
					float alcadaBrac = resultSet.getFloat("alcadaBrac");
					if (resultSet.wasNull())
						registre = new RegistreEspatlla(
								resultSet.getString("numeroDeRegistre"),
								DateParser.parseZonedDateTime(resultSet.getTimestamp("dataHora")),
								casteller,
								alcadaEspatlla
						);
					else
						registre = new RegistreComplet(
								resultSet.getString("numeroDeRegistre"),
								DateParser.parseZonedDateTime(resultSet.getTimestamp("dataHora")),
								casteller,
								alcadaEspatlla,
								alcadaBrac
						);
				}

				casteller.addRegistre(registre);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
