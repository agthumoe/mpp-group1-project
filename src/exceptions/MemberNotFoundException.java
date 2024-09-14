package exceptions;

import java.io.Serializable;

public class MemberNotFoundException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -4802933341371261839L;

    public MemberNotFoundException() {
        super();
    }

    public MemberNotFoundException(String msg) {
        super(msg);
    }

    public MemberNotFoundException(Throwable t) {
        super(t);
    }
}
