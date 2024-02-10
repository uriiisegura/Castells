package persistence.dao;

import exceptions.SqlConnectionException;
import models.castells.Estructura;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EstructuraSqlDAO {
	private final static String tableName = "Estructura";

	private final Connection connection;

	public EstructuraSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Estructura> loadAll() {
		List<Estructura> estructures = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				estructures.add(new Estructura(
						resultSet.getString("notacio"),
						resultSet.getString("nom")
				));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return estructures;
	}
}
