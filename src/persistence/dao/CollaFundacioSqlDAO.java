package persistence.dao;

import config.DateParser;
import exceptions.SqlConnectionException;
import models.colles.Colla;
import models.relationships.CollaFundacio;

import java.sql.*;
import java.util.List;

public class CollaFundacioSqlDAO {
	private final static String tableName = "CollaFundacio";

	private final Connection connection;

	public CollaFundacioSqlDAO(Connection connection) {
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

				colla.addFundacio(new CollaFundacio(
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

	public boolean add(String collaId, CollaFundacio collaFundacio) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					String.format("INSERT INTO %s (colla, desDe, finsA) VALUES (?, ?, ?)", tableName)
			);
			preparedStatement.setString(1, collaId);
			preparedStatement.setDate(2, Date.valueOf(collaFundacio.getDesDe()));
			preparedStatement.setDate(3, collaFundacio.getFinsA() != null ? Date.valueOf(collaFundacio.getFinsA()) : null);
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
