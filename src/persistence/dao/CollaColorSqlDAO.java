package persistence.dao;

import config.DateParser;
import exceptions.SqlConnectionException;
import models.colles.Colla;
import models.relationships.CollaColor;

import java.awt.*;
import java.sql.*;
import java.util.List;

public class CollaColorSqlDAO {
	private final static String tableName = "CollaColor";

	private final Connection connection;

	public CollaColorSqlDAO(Connection connection) {
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

				colla.addColor(new CollaColor(
						Color.decode(resultSet.getString("color")),
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

	public boolean add(String collaId, CollaColor collaColor) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					String.format("INSERT INTO %s (colla, desDe, finsA, color) VALUES (?, ?, ?, ?)", tableName)
			);
			preparedStatement.setString(1, collaId);
			preparedStatement.setDate(2, Date.valueOf(collaColor.getDesDe()));
			preparedStatement.setDate(3, collaColor.getFinsA() != null ? Date.valueOf(collaColor.getFinsA()) : null);
			preparedStatement.setString(4, collaColor.getHexColor());
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
