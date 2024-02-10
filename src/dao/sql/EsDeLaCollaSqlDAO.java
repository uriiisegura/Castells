package dao.sql;

import config.DateParser;
import dao.EsDeLaCollaDAO;
import exceptions.SqlConnectionException;
import models.castellers.Casteller;
import models.colles.Colla;
import relationships.EsDeLaColla;

import java.sql.*;
import java.util.List;

public class EsDeLaCollaSqlDAO implements EsDeLaCollaDAO {
	private final static String tableName = "EsDeLaColla";

	private final Connection connection;

	public EsDeLaCollaSqlDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
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

				EsDeLaColla pertany = new EsDeLaColla(casteller, colla, resultSet.getDate("desDe").toLocalDate(), DateParser.parseLocalDate(resultSet.getDate("finsA")), resultSet.getString("malnom"));
				colla.addCasteller(pertany);
				casteller.addColla(pertany);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
