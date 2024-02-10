package persistence.dao.sql;

import config.DateParser;
import persistence.dao.DiadaDAO;
import exceptions.SqlConnectionException;
import models.colles.Colla;
import models.colles.CollaConvencional;
import models.colles.CollaUniversitaria;
import models.diades.Diada;
import models.locations.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DiadaSqlDAO implements DiadaDAO {
	private final static String tableName = "Diada";

	private final Connection connection;

	public DiadaSqlDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Diada> loadAll(List<Location> locations) {
		List<Diada> diades = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String locationId = resultSet.getString("location");
				Location location = locations.stream().filter(l -> l.getId().equals(locationId)).findFirst().orElse(null);

				if (location == null) {
					// TODO:
					throw new SQLException("Location not found");
				}

				diades.add(new Diada(
						resultSet.getString("id"),
						resultSet.getString("nom"),
						DateParser.parseZonedDateTime(resultSet.getTimestamp("inici")),
						DateParser.parseZonedDateTime(resultSet.getTimestamp("fi")),
						location
				));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return diades;
	}
}
