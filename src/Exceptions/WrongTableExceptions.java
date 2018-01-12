package Exceptions;

public class WrongTableExceptions extends Throwable {

    @Override
    public String getMessage() {
        return "Wrong table name";
    }
}
