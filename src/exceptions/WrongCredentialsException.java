package exceptions;

public class WrongCredentialsException extends Exception {
	public WrongCredentialsException() {
		super("Credencials incorrectes.");
	}
}
