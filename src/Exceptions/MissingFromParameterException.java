package Exceptions;

public class MissingFromParameterException extends Throwable {

    @Override
    public String getMessage() {
        return "Missing SELECT Value";
    }
}
