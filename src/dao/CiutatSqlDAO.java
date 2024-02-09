package dao;

import exceptions.SqlConnectionException;
import models.locations.Ciutat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CiutatSqlDAO {
	private final static String tableName = "Ciutat";

	private final Connection connection;

	public CiutatSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Ciutat> loadAll() {
		List<Ciutat> ciutats = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				ciutats.add(new Ciutat(resultSet.getString("id"), resultSet.getString("nom")));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return ciutats;
	}
}
