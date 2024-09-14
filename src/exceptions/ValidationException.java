package exceptions;

import java.io.Serializable;

public class ValidationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -7146550803287797291L;

    public ValidationException() {
        super();
    }

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(Throwable t) {
        super(t);
    }
}
