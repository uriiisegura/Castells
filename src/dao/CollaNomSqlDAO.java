package dao;

import exceptions.SqlConnectionException;
import models.Colla;
import relationships.CollaNom;

import java.sql.*;
import java.time.LocalDate;
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
				Date finsA = resultSet.getDate("finsA");
				LocalDate finsALocalDate = resultSet.wasNull() ? null : finsA.toLocalDate();

				Colla colla = colles.stream().filter(c -> c.getId().equals(collaId)).findFirst().orElse(null);

				if (colla == null) {
					// TODO:
					throw new SQLException("Colla not found");
				}

				colla.addNom(new CollaNom(
						resultSet.getString("nom"),
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
