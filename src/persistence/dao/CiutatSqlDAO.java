package persistence.dao;

import exceptions.SqlConnectionException;
import models.locations.Ciutat;
import models.locations.Pais;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiutatSqlDAO {
	private final static String tableName = "Ciutat";

	private final Connection connection;

	public CiutatSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Ciutat> loadAll(List<Pais> paissos) {
		List<Ciutat> ciutats = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String paisNom = resultSet.getString("pais");
				Pais pais = paissos.stream().filter(p -> p.getNom().equals(paisNom)).findFirst().orElse(null);

				if (pais == null) {
					// TODO:
					throw new SQLException("No existeix el país.");
				}

				Ciutat ciutat = new Ciutat(resultSet.getString("nom"), pais);
				ciutats.add(ciutat);
				pais.addCiutat(ciutat);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return ciutats;
	}

	public boolean add(Ciutat ciutat) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					String.format("INSERT INTO %s (nom, pais) VALUES (?, ?)", tableName)
			);
			preparedStatement.setString(1, ciutat.getNom());
			preparedStatement.setString(2, ciutat.getPais().getNom());
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
