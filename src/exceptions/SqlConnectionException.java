package exceptions;

public class SqlConnectionException extends RuntimeException {
	public SqlConnectionException() {
		super("Error connecting to the database. Please check the connection and the credentials and try again.");
	}
}
