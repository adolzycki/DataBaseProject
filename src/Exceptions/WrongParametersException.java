package Exceptions;

public class WrongParametersException extends Throwable {

    @Override
    public String getMessage() {
        return "Wrong parameters for this function";
    }

}
