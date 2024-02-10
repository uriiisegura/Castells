package dao.sql;

import dao.CastellLineUpDAO;
import exceptions.SqlConnectionException;
import models.castellers.Casteller;
import models.castells.Rengla;
import models.diades.CastellLineUp;
import models.diades.RenglaLineUp;
import relationships.CastellDiada;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

public class CastellLineUpSqlDAO implements CastellLineUpDAO {
	private final static String pomTableName = "PomLineUp"; // TODO:
	private final static String renglaTableName = "RenglaLineUp";
	private final static String renglaLineUpTableName = "RenglaLineUpCastellers";

	private final Connection connection;

	public CastellLineUpSqlDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void loadAll(List<Casteller> castellers, List<CastellDiada> castells, List<Rengla> rengles) {
		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", renglaTableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				long castellId = resultSet.getLong("castell");
				String renglaNom = resultSet.getString("renglaNom");

				CastellDiada castell = castells.stream().filter(c -> c.getId() == castellId).findFirst().orElse(null);
				if (castell == null) {
					// TODO:
					throw new SQLException("Castell not found");
				}

				Rengla rengla = rengles.stream().filter(r -> r.getNom().equals(renglaNom) && r.getEstructura().getNotacio().equals(castell.getEstructura().getNotacio())).findFirst().orElse(null);
				if (rengla == null) {
					// TODO:
					throw new SQLException("Rengla not found");
				}

				Statement subStatement = connection.createStatement();
				String subSelectQuery = String.format("SELECT * FROM %s WHERE renglaLineUp = %d ORDER BY pis", renglaLineUpTableName, resultSet.getLong("id"));
				ResultSet subResultSet = subStatement.executeQuery(subSelectQuery);

				Vector<Casteller> castellersInRengla = new Vector<>();
				while (subResultSet.next()) {
					String castellerId = subResultSet.getString("casteller");

					Casteller casteller = castellers.stream().filter(c -> c.getDni().equals(castellerId)).findFirst().orElse(null);

					if (casteller == null) {
						// TODO:
						throw new SQLException("Casteller not found");
					}

					castellersInRengla.add(casteller);
				}

				CastellLineUp renglaLineUp = new RenglaLineUp(
						castell,
						rengla,
						castellersInRengla
				);

				castell.addLineUp(renglaLineUp);
				for (Casteller casteller : castellersInRengla) {
					casteller.addCastell(renglaLineUp);
				}

				subResultSet.close();
				subStatement.close();
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
