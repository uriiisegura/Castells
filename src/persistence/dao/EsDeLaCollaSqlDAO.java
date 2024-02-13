package persistence.dao;

import config.DateParser;
import exceptions.SqlConnectionException;
import models.castellers.Casteller;
import models.colles.Colla;
import models.relationships.EsDeLaColla;

import java.sql.*;
import java.util.List;

public class EsDeLaCollaSqlDAO {
	private final static String tableName = "EsDeLaColla";

	private final Connection connection;

	public EsDeLaCollaSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public void loadAll(List<Casteller> castellers, List<Colla> colles) {
		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String castellerId = resultSet.getString("casteller");
				String collaId = resultSet.getString("colla");

				Casteller casteller = castellers.stream().filter(c -> c.getDni().equals(castellerId)).findFirst().orElse(null);
				Colla colla = colles.stream().filter(c -> c.getId().equals(collaId)).findFirst().orElse(null);

				if (casteller == null) {
					// TODO:
					throw new SQLException("Casteller not found");
				}
				if (colla == null) {
					// TODO:
					throw new SQLException("Colla not found");
				}

				EsDeLaColla pertany = new EsDeLaColla(
						casteller,
						colla,
						DateParser.parseLocalDate(resultSet.getDate("desDe")),
						DateParser.parseLocalDate(resultSet.getDate("finsA")),
						resultSet.getString("malnom")
				);
				colla.addCasteller(pertany);
				casteller.addColla(pertany);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}

	public void add(EsDeLaColla esDeLaColla) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					String.format("INSERT INTO %s (casteller, colla, desDe, finsA, malnom) VALUES (?, ?, ?, ?, ?)", tableName)
			);
			preparedStatement.setString(1, esDeLaColla.getCasteller().getDni());
			preparedStatement.setString(2, esDeLaColla.getColla().getId());
			preparedStatement.setDate(3, Date.valueOf(esDeLaColla.getDesDe()));
			preparedStatement.setDate(4, esDeLaColla.getFinsA() != null ? Date.valueOf(esDeLaColla.getFinsA()) : null);
			preparedStatement.setString(5, esDeLaColla.getMalnom());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
