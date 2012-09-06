package SongGenerator;

/**
 * Generation Exception class for Song generator. These are used when something strange 
 * occurs with song generation.
 * This contains and enum for the different causes.
 * @author Alicia Bendz
 *
 */
@SuppressWarnings({ "serial"})
public class GenerationException extends Exception {

	/**
	 * Enum for possible causes.
	 */
	public enum Cause {REQUEST_MALFORMED, DATABASE, OTHER};

	/**
	 * The cause for the exception.
	 */
	private Cause mCause;

	/**
	 * Constructor with just the Generation Exception on it's own.
	 * @param source The class that generated the exception, usually the generator responsible.
	 * @param message The description of the cause exception.
	 * @param cause  The general cause of the exception.
	 */
	public GenerationException(String source, String message, Cause cause){
		super(source + ": " + message);
		mCause = cause;
	}
	
	/**
	 * Constructor that will nest exceptions.
	 * @param source The class that generated the exception, usually the generator responsible.
	 * @param message The description of the cause.
	 * @param cause The general cause.
	 * @param e The original exception.
	 */
	public GenerationException(String source, String message, Cause cause, Exception e){
		super(source + ": " + message, e);
		mCause = cause;
	}
	
	/**
	 * Method to get the cause of the exception.
	 * @return The cause for the exception.
	 */
	public Cause getGenerationCause(){
		return mCause;
	}

}
