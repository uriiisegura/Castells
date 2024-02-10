package persistence.dao;

import config.DateParser;
import exceptions.SqlConnectionException;
import models.castellers.Casteller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CastellerSqlDAO {
	private final static String tableName = "Casteller";

	private final Connection connection;

	public CastellerSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Casteller> loadAll() {
		List<Casteller> castellers = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				castellers.add(new Casteller(
						resultSet.getString("dni"),
						resultSet.getString("nom"),
						resultSet.getString("cognom1"),
						resultSet.getString("cognom2"),
						resultSet.getString("sexe"),
						resultSet.getDate("dataNaixement").toLocalDate(),
						DateParser.parseLocalDate(resultSet.getDate("dataDefuncio"))
				));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return castellers;
	}
}
