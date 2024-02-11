package persistence.dao;

import exceptions.SqlConnectionException;
import models.locations.Pais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaisSqlDAO {
	private final static String tableName = "Pais";

	private final Connection connection;

	public PaisSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Pais> loadAll() {
		List<Pais> paissos = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				paissos.add(new Pais(resultSet.getString("nom")));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return paissos;
	}
}
