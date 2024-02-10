package persistence.dao;

import exceptions.SqlConnectionException;
import models.castells.Reforcos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReforcosSqlDAO {
	private final static String tableName = "Reforcos";

	private final Connection connection;

	public ReforcosSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Reforcos> loadAll() {
		List<Reforcos> reforcos = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				reforcos.add(new Reforcos(
						resultSet.getString("notacio"),
						resultSet.getString("nom")
				));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return reforcos;
	}
}
