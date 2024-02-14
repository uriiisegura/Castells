package persistence;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import config.Encryptor;
import exceptions.ConfigFileNotFoundException;
import exceptions.SqlConnectionException;
import exceptions.WrongCredentialsException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
import java.util.HashMap;

public class SqlConnection {
	private final String path = "config.json";
	private Connection connection;

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

	public Connection getConnection() {
		return connection;
	}

	public String logIn(String identifier, String password) throws WrongCredentialsException {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Usuari WHERE casteller=? AND password=?");
			statement.setString(1, identifier);
			statement.setString(2, Encryptor.sha256(password));
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("rol");
			} else {
				throw new WrongCredentialsException();
			}
		} catch (SQLException e) {
			// TODO:
			throw new RuntimeException(e);
		}
	}

	private record Credentials(String user, String password, String url) {}
}
