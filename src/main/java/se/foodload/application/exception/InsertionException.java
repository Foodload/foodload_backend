package se.foodload.application.exception;

public class InsertionException extends RuntimeException {
    private Object errorObject;

    public InsertionException(String msg, Object errorObject) {
        super(msg);
        this.errorObject = errorObject;
    }

    public InsertionException(String msg) {
        super(msg);
    }

    public Object getErrorObject() {
        return this.errorObject;
    }
}
