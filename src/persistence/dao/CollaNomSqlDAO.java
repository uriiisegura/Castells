package persistence.dao;

import config.DateParser;
import exceptions.SqlConnectionException;
import models.colles.Colla;
import models.relationships.CollaNom;

import java.sql.*;
import java.util.List;

public class CollaNomSqlDAO {
	private final static String tableName = "CollaNom";

	private final Connection connection;

	public CollaNomSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public void loadAll(List<Colla> colles) {
		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String collaId = resultSet.getString("colla");
				Colla colla = colles.stream().filter(c -> c.getId().equals(collaId)).findFirst().orElse(null);

				if (colla == null) {
					// TODO:
					throw new SQLException("Colla not found");
				}

				colla.addNom(new CollaNom(
						resultSet.getString("nom"),
						DateParser.parseLocalDate(resultSet.getDate("desDe")),
						DateParser.parseLocalDate(resultSet.getDate("finsA"))
				));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}

	public boolean add(String collaId, CollaNom collaNom) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					String.format("INSERT INTO %s (colla, desDe, finsA, nom) VALUES (?, ?, ?, ?)", tableName)
			);
			preparedStatement.setString(1, collaId);
			preparedStatement.setDate(2, Date.valueOf(collaNom.getDesDe()));
			preparedStatement.setDate(3, collaNom.getFinsA() != null ? Date.valueOf(collaNom.getFinsA()) : null);
			preparedStatement.setString(4, collaNom.getNom());
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
