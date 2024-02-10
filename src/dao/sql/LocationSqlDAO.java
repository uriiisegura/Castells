package dao.sql;

import dao.LocationDAO;
import exceptions.SqlConnectionException;
import models.locations.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationSqlDAO implements LocationDAO {
	private final static String tableName = "Location";

	private final Connection connection;

	public LocationSqlDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Location> loadAll(List<Ciutat> ciutats) {
		List<Location> locations = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String type = resultSet.getString("type");
				String ciutatId = resultSet.getString("ciutat");

				Ciutat ciutat = ciutats.stream().filter(c -> c.getId().equals(ciutatId)).findFirst().orElse(null);

				if (ciutat == null) {
					// TODO:
					throw new SQLException("Ciutat not found");
				}

				Location location;
				switch (type) {
					case "plaça":
						location = new Placa(
							resultSet.getString("id"),
							resultSet.getString("nom"),
							ciutat
						);
						break;
					case "carrer":
						location = new Carrer(
							resultSet.getString("id"),
							resultSet.getString("nom"),
							ciutat
						);
						break;
					case "edifici":
						location = new Edifici(
							resultSet.getString("id"),
							resultSet.getString("nom"),
							ciutat
						);
						break;
					default:
						// TODO:
						continue;
				}

				locations.add(location);
				ciutat.addLocation(location);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return locations;
	}
}
