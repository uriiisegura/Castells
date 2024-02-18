package persistence.dao;

import exceptions.SqlConnectionException;
import models.colles.Colla;
import models.colles.CollaConvencional;
import models.colles.CollaUniversitaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollaSqlDAO {
	private final static String tableName = "Colla";

	private final Connection connection;

	public CollaSqlDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Colla> loadAll() {
		List<Colla> colles = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			String selectQuery = String.format("SELECT * FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next()) {
				if (resultSet.getBoolean("universitaria"))
					colles.add(new CollaUniversitaria(resultSet.getString("id")));
				else
					colles.add(new CollaConvencional(resultSet.getString("id")));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}

		return colles;
	}

	public boolean add(Colla colla) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(String.format("INSERT INTO %s (id, universitaria) VALUES (?, ?)", tableName));
			preparedStatement.setString(1, colla.getId());
			preparedStatement.setBoolean(2, colla instanceof CollaUniversitaria);
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}
}
