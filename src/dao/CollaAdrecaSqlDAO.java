package dao;

import config.DateParser;
import exceptions.SqlConnectionException;
import models.locations.Ciutat;
import models.colles.Colla;
import relationships.CollaAdreca;

import java.sql.*;
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
						DateParser.parseLocalDate(resultSet.getDate("finsA"))
				));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
