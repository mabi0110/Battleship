package exceptions;

public class TooCloseToAnotherOneException extends Exception{

    public TooCloseToAnotherOneException(String message) {
        super(message);
    }
}
