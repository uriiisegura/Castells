package persistence.dao.sql;

import config.DateParser;
import persistence.dao.CollaFundacioDAO;
import exceptions.SqlConnectionException;
import models.colles.Colla;
import relationships.CollaFundacio;

import java.sql.*;
import java.util.List;

public class CollaFundacioSqlDAO implements CollaFundacioDAO {
	private final static String tableName = "CollaFundacio";

	private final Connection connection;

	public CollaFundacioSqlDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
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
