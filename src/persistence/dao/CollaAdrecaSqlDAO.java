package persistence.dao;

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
				String ciutatNom = resultSet.getString("ciutat");
				String paisNom = resultSet.getString("pais");

				Colla colla = colles.stream().filter(c -> c.getId().equals(collaId)).findFirst().orElse(null);
				Ciutat ciutat = ciutats.stream().filter(c -> c.getNom().equals(ciutatNom) && c.getPais().getNom().equals(paisNom)).findFirst().orElse(null);

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

	public void add(String collaId, CollaAdreca collaAdreca) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					String.format("INSERT INTO %s (colla, adreca, ciutat, pais, desDe, finsA) VALUES (?, ?, ?, ?, ?, ?)", tableName)
			);
			preparedStatement.setString(1, collaId);
			preparedStatement.setString(2, collaAdreca.getAdreca());
			preparedStatement.setString(3, collaAdreca.getCiutatNom());
			preparedStatement.setString(4, collaAdreca.getPaisNom());
			preparedStatement.setDate(5, Date.valueOf(collaAdreca.getDesDe()));
			preparedStatement.setDate(6, collaAdreca.getFinsA() != null ? Date.valueOf(collaAdreca.getFinsA()) : null);

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
