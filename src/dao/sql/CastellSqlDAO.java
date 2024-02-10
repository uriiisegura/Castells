package dao.sql;

import exceptions.SqlConnectionException;
import models.castells.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CastellSqlDAO {
	private final static String tableName = "Castell";

	private final Connection connection;

	public CastellSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Castell> loadAll(List<Estructura> estructures, List<Pisos> pisos, List<Reforcos> reforcos) {
		List<Castell> castells = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				String estructuraNotacio = resultSet.getString("estructura");
				String pisosNotacio = resultSet.getString("pisos");
				String reforcosNotacio = resultSet.getString("reforcos");

				Estructura estructura = estructures.stream().filter(e -> e.getNotacio().equals(estructuraNotacio)).findFirst().orElse(null);
				Pisos pis = pisos.stream().filter(p -> p.getNotacio().equals(pisosNotacio)).findFirst().orElse(null);
				Reforcos reforc = reforcos.stream().filter(r -> r.getNotacio().equals(reforcosNotacio)).findFirst().orElse(null);

				if (estructura == null) {
					// TODO:
					throw new SQLException("Estructura not found");
				}
				if (pis == null) {
					// TODO:
					throw new SQLException("Pis not found");
				}
				if (reforc == null) {
					// TODO:
					throw new SQLException("Reforç not found");
				}

				Castell castell;

				if (resultSet.getBoolean("universitari"))
					castell = new CastellUniversitari(
							resultSet.getString("id"),
							estructura,
							pis,
							reforc,
							resultSet.getInt("agulles"),
							resultSet.getBoolean("perSota"),
							resultSet.getBoolean("caminant"),
							resultSet.getInt("enxanetes")
					);
				else
					castell = new CastellConvencional(
							resultSet.getString("id"),
							estructura,
							pis,
							reforc,
							resultSet.getInt("agulles"),
							resultSet.getBoolean("perSota"),
							resultSet.getBoolean("caminant"),
							resultSet.getInt("enxanetes")
					);

				castells.add(castell);
				estructura.addCastell(castell);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return castells;
	}
}
