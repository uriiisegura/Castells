package exceptions;

public class ConfigFileNotFoundException extends RuntimeException {
	public ConfigFileNotFoundException() {
		super("Config file not found. Please make sure that the file config.json is in the root of the project.");
	}
}
