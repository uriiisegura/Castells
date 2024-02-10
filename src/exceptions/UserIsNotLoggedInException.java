package exceptions;

public class UserIsNotLoggedInException extends Exception {
	public UserIsNotLoggedInException() {
		super("Sessió no iniciada.");
	}
}
