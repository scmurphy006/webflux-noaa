package com.smurphy.interview.excpetion;

/**
 * Custom runtime exception that could be used for custom error handling if need
 * be
 */
public class IllegalForecastException extends RuntimeException {

    /**
     * Creates instance of IllegalForecastException
     * 
     * @param message The message to pass along to exception handler
     * @param cause   The cause of this exception
     */
    public IllegalForecastException(String message, Throwable cause) {
        super(message, cause);
    }
}
