package persistence.dao;

import enums.ResultatsT;
import exceptions.SqlConnectionException;
import models.castells.Castell;
import models.colles.Colla;
import relationships.CastellDiada;
import models.diades.Diada;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CastellDiadaSqlDAO {
	private final static String tableName = "CastellDiada";

	private final Connection connection;

	public CastellDiadaSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<CastellDiada> loadAll(List<Diada> diades, List<Castell> castells, List<Colla> colles) {
		List<CastellDiada> castellDiades = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s ORDER BY id, ordre", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String diadaId = resultSet.getString("diada");
				String castellId = resultSet.getString("castell");
				String collaId = resultSet.getString("colla");

				Diada diada = diades.stream().filter(d -> d.getId().equals(diadaId)).findFirst().orElse(null);
				Castell castell = castells.stream().filter(c -> c.getId().equals(castellId)).findFirst().orElse(null);
				Colla colla = colles.stream().filter(c -> c.getId().equals(collaId)).findFirst().orElse(null);

				if (diada == null) {
					// TODO:
					throw new SQLException("Diada not found");
				}
				if (castell == null) {
					// TODO:
					throw new SQLException("Castell not found");
				}
				if (colla == null) {
					// TODO:
					throw new SQLException("Colla not found");
				}

				CastellDiada castellDiada = new CastellDiada(
						resultSet.getLong("id"),
						diada,
						castell,
						ResultatsT.valueOf(resultSet.getString("resultat")),
						colla,
						resultSet.getInt("ordre")
				);

				diada.addCastell(castellDiada);
				castell.addFet(castellDiada);
				colla.addCastell(castellDiada);
				castellDiades.add(castellDiada);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return castellDiades;
	}
}
