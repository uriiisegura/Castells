package persistence.dao;

import exceptions.SqlConnectionException;
import models.castells.Pisos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PisosSqlDAO {
	private final static String tableName = "Pisos";

	private final Connection connection;

	public PisosSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Pisos> loadAll() {
		List<Pisos> pisos = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				pisos.add(new Pisos(
						resultSet.getString("notacio"),
						resultSet.getString("nom")
				));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return pisos;
	}
}
