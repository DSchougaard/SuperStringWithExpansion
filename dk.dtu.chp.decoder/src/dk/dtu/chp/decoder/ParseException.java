package dk.dtu.chp.decoder;

@SuppressWarnings("serial")
public class ParseException extends Exception {

	private String errorCause;
	
	public ParseException(String errorCause) {
		super("Default parse exception");
		this.errorCause = errorCause;
	}

	public ParseException(String message, String errorCause) {
		super(message);
		this.errorCause = errorCause;
	}
	
	public String getErrorCause(){
		return this.errorCause;
	}

}
