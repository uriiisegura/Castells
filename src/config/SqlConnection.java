package config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import exceptions.ConfigFileNotFoundException;
import exceptions.SqlConnectionException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
	private final String path = "config.json";
	public Connection connection;

	public SqlConnection() {
		Credentials credentials;

		Gson gson = new Gson();
		try {
			JsonReader jsonReader = new JsonReader(new FileReader(path));
			credentials = gson.fromJson(jsonReader, Credentials.class);
		} catch (FileNotFoundException e) {
			throw new ConfigFileNotFoundException();
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(credentials.url, credentials.user, credentials.password);
		} catch (ClassNotFoundException ignored) {
		} catch (SQLException e) {
			throw new SqlConnectionException();
		}
	}

	private class Credentials {
		private final String user;
		private final String password;
		private final String url;

		public Credentials(String user, String password, String url) {
			this.user = user;
			this.password = password;
			this.url = url;
		}
	}
}
