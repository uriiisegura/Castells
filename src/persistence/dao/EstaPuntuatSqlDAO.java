package persistence.dao;

import config.DateParser;
import exceptions.SqlConnectionException;
import models.castells.Castell;
import models.relationships.EstaPuntuat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EstaPuntuatSqlDAO {
	private final static String tableName = "EstaPuntuat";

	private final Connection connection;

	public EstaPuntuatSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<EstaPuntuat> loadAll(List<Castell> castells) {
		List<EstaPuntuat> puntuats = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String castellId = resultSet.getString("castell");
				Castell castell = castells.stream().filter(c -> c.getId().equals(castellId)).findFirst().orElse(null);

				if (castell == null) {
					// TODO:
					throw new SQLException("Castell not found");
				}

				EstaPuntuat estaPuntuat = new EstaPuntuat(
						castell,
						DateParser.parseLocalDate(resultSet.getDate("desDe")),
						DateParser.parseLocalDate(resultSet.getDate("finsA")),
						resultSet.getInt("carregat"),
						resultSet.getInt("descarregat"),
						resultSet.getInt("grup"),
						resultSet.getInt("subgrup")
				);
				puntuats.add(estaPuntuat);
				castell.addPuntuacio(estaPuntuat);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return puntuats;
	}
}
