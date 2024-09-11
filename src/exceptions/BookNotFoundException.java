package exceptions;

import java.io.Serializable;

public class BookNotFoundException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -7146550803287797291L;

    public BookNotFoundException() {
        super();
    }
    public BookNotFoundException(String msg) {
        super(msg);
    }
    public BookNotFoundException(Throwable t) {
        super(t);
    }
}
