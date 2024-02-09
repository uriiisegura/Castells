package dao;

import exceptions.SqlConnectionException;
import models.castells.Estructura;
import models.castells.Rengla;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RenglaSqlDAO {
	private final static String tableName = "Rengla";

	private final Connection connection;

	public RenglaSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Rengla> loadAll(List<Estructura> estructures) {
		List<Rengla> rengles = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String estructuraNotacio = resultSet.getString("estructura");
				Estructura estructura = estructures.stream().filter(e -> e.getNotacio().equals(estructuraNotacio)).findFirst().orElse(null);

				if (estructura == null) {
					// TODO:
					throw new SQLException("Estructura not found.");
				}

				Rengla rengla = new Rengla(
						estructura,
						resultSet.getString("nom")
				);

				rengles.add(rengla);
				estructura.addRengla(rengla);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return rengles;
	}
}
