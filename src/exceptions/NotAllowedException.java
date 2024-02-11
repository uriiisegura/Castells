package exceptions;

public class NotAllowedException extends Exception {
	public NotAllowedException() {
		super("No tens permís per a realitzar aquesta acció.");
	}
}
