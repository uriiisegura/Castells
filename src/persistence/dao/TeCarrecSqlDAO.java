package persistence.dao;

import config.DateParser;
import exceptions.SqlConnectionException;
import models.castellers.Casteller;
import models.colles.Carrec;
import models.colles.Colla;
import relationships.TeCarrec;

import java.sql.*;
import java.util.List;

public class TeCarrecSqlDAO {
	private final static String tableName = "TeCarrec";

	private final Connection connection;

	public TeCarrecSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public void loadAll(List<Casteller> castellers, List<Colla> colles, List<Carrec> carrecs) {
		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String castellerId = resultSet.getString("casteller");
				String collaId = resultSet.getString("colla");
				String carrecId = resultSet.getString("carrec");

				Casteller casteller = castellers.stream().filter(c -> c.getDni().equals(castellerId)).findFirst().orElse(null);
				Colla colla = colles.stream().filter(c -> c.getId().equals(collaId)).findFirst().orElse(null);
				Carrec carrec = carrecs.stream().filter(c -> c.getMasculi().equals(carrecId)).findFirst().orElse(null);

				if (casteller == null) {
					// TODO:
					throw new SQLException("Casteller not found");
				}
				if (colla == null) {
					// TODO:
					throw new SQLException("Colla not found");
				}
				if (carrec == null) {
					// TODO:
					throw new SQLException("Carrec not found");
				}

				TeCarrec teCarrec = new TeCarrec(casteller, colla, carrec, resultSet.getDate("desDe").toLocalDate(), DateParser.parseLocalDate(resultSet.getDate("finsA")));
				casteller.addCarrec(teCarrec);
				colla.addCarrec(teCarrec);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
