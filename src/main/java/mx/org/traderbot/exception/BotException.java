package mx.org.traderbot.exception;

public class BotException extends Exception{

	private static final long serialVersionUID = 1L;
	
	ErrorBot error;

	public ErrorBot getError() {
		return error;
	}

	public void setError(ErrorBot error) {
		this.error = error;
	}
	
	
}
