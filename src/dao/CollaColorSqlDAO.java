package dao;

import exceptions.SqlConnectionException;
import models.Colla;
import relationships.CollaColor;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
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
				Date finsA = resultSet.getDate("finsA");
				LocalDate finsALocalDate = resultSet.wasNull() ? null : finsA.toLocalDate();

				Colla colla = colles.stream().filter(c -> c.getId().equals(collaId)).findFirst().orElse(null);

				if (colla == null) {
					// TODO:
					throw new SQLException("Colla not found");
				}

				colla.addColor(new CollaColor(
						Color.decode(resultSet.getString("color")),
						resultSet.getDate("desDe").toLocalDate(),
						finsALocalDate
				));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
