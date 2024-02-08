package dao;

import exceptions.SqlConnectionException;
import models.Carrec;
import models.CarrecJunta;
import models.CarrecTecnica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarrecSqlDAO {
	private final static String tableName = "Carrec";

	private final Connection connection;

	public CarrecSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Carrec> loadAll() {
		List<Carrec> carrecs = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				switch (resultSet.getString("area")) {
					case "tecnica":
						carrecs.add(new CarrecTecnica(
								resultSet.getString("masculi"),
								resultSet.getString("femeni"),
								resultSet.getString("neutre")
						));
						break;
					case "junta":
						carrecs.add(new CarrecJunta(
								resultSet.getString("masculi"),
								resultSet.getString("femeni"),
								resultSet.getString("neutre")
						));
						break;
					default:
						// TODO:
						throw new SQLException("Invalid area");
				}
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return carrecs;
	}
}
