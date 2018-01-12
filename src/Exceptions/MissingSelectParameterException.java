package Exceptions;

public class MissingSelectParameterException extends Throwable {

    @Override
    public String getMessage() {
        return "Missing SELECT Value";
    }
}
