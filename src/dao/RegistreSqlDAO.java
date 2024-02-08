package dao;

import exceptions.SqlConnectionException;
import models.*;

import java.sql.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistreSqlDAO {
	private final static String tableName = "Registre";

	private final Connection connection;

	public RegistreSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Registre> loadAll(List<Casteller> castellers) {
		List<Registre> registres = new ArrayList<>();

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

				float alcadaEspatlla = resultSet.getFloat("alcadaEspatlla");
				if (resultSet.wasNull()) {
					float alcadaBrac = resultSet.getFloat("alcadaBrac");
					registres.add(new RegistreBrac(resultSet.getString("numeroDeRegistre"), resultSet.getTimestamp("dataHora").toLocalDateTime().atZone(ZoneOffset.UTC), casteller, alcadaBrac));
				} else {
					float alcadaBrac = resultSet.getFloat("alcadaBrac");
					if (resultSet.wasNull())
						registres.add(new RegistreEspatlla(resultSet.getString("numeroDeRegistre"), resultSet.getTimestamp("dataHora").toLocalDateTime().atZone(ZoneOffset.UTC), casteller, alcadaEspatlla));
					else
						registres.add(new RegistreComplet(resultSet.getString("numeroDeRegistre"), resultSet.getTimestamp("dataHora").toLocalDateTime().atZone(ZoneOffset.UTC), casteller, alcadaEspatlla, alcadaBrac));
				}
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return registres;
	}
}
