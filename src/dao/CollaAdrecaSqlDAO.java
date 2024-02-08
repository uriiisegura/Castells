package dao;

import exceptions.SqlConnectionException;
import models.Ciutat;
import models.Colla;
import relationships.CollaAdreca;
import relationships.CollaNom;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class CollaAdrecaSqlDAO {
	private final static String tableName = "CollaAdreca";

	private final Connection connection;

	public CollaAdrecaSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public void loadAll(List<Colla> colles, List<Ciutat> ciutats) {
		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String collaId = resultSet.getString("colla");
				String ciutatId = resultSet.getString("ciutat");
				Date finsA = resultSet.getDate("finsA");
				LocalDate finsALocalDate = resultSet.wasNull() ? null : finsA.toLocalDate();

				Colla colla = colles.stream().filter(c -> c.getId().equals(collaId)).findFirst().orElse(null);
				Ciutat ciutat = ciutats.stream().filter(c -> c.getId().equals(ciutatId)).findFirst().orElse(null);

				if (colla == null) {
					// TODO:
					throw new SQLException("Colla not found");
				}
				if (ciutat == null) {
					// TODO:
					throw new SQLException("Ciutat not found");
				}

				colla.addAdreca(new CollaAdreca(
						resultSet.getString("adreca"),
						ciutat,
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
