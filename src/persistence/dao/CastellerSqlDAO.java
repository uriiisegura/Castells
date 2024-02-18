package persistence.dao;

import config.DateParser;
import exceptions.SqlConnectionException;
import models.castellers.Casteller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CastellerSqlDAO {
	private final static String tableName = "Casteller";

	private final Connection connection;

	public CastellerSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Casteller> loadAll() {
		List<Casteller> castellers = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				castellers.add(new Casteller(
						resultSet.getString("dni"),
						resultSet.getString("nom"),
						resultSet.getString("cognom1"),
						resultSet.getString("cognom2"),
						resultSet.getString("sexe"),
						resultSet.getString("email"),
						DateParser.parseLocalDate(resultSet.getDate("dataNaixement")),
						DateParser.parseLocalDate(resultSet.getDate("dataDefuncio"))
				));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return castellers;
	}

	public boolean add(Casteller casteller) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					String.format("INSERT INTO %s (dni, nom, cognom1, cognom2, sexe, email, dataNaixement, dataDefuncio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", tableName)
			);
			preparedStatement.setString(1, casteller.getDni());
			preparedStatement.setString(2, casteller.getNom());
			preparedStatement.setString(3, casteller.getCognom1());
			preparedStatement.setString(4, casteller.getCognom2());
			preparedStatement.setString(5, casteller.getSexe());
			preparedStatement.setString(6, casteller.getEmail());
			preparedStatement.setDate(7, Date.valueOf(casteller.getDataNaixement()));
			preparedStatement.setDate(8, casteller.getDataDefuncio() != null ? Date.valueOf(casteller.getDataDefuncio()) : null);
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
