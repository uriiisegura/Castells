package business.dto;

public class LogInDTO {
	private final String identifier;
	private final String password;

	public LogInDTO(String identifier, String password) {
		this.identifier = identifier;
		this.password = password;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getPassword() {
		return password;
	}
}
