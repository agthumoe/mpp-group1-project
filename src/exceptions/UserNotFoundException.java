package exceptions;

import java.io.Serializable;

public class UserNotFoundException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1992170182712656780L;

    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(String msg) {
        super(msg);
    }
    public UserNotFoundException(Throwable t) {
        super(t);
    }
}
