package streams.exception;

/**
 * Represents an exception specific to the Streams application.
 * This exception is used to indicate errors that occur during the execution of the program.
 */
public class StreamsException extends Exception {
    /**
     * Constructs a new DrBrownException with the specified detail message.
     *
     * @param message The detail message for the exception.
     */
    public StreamsException(String message) {
        super(message);
    }
}

