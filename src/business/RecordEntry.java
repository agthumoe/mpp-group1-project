package business;

import java.io.Serializable;
import java.time.LocalDate;

public class RecordEntry implements Serializable {
    private static final long serialVersionUID = 7508483940558530471L;

    private LocalDate dateOfCheckout;
    private LocalDate dueDate;

    public RecordEntry(LocalDate dateOfCheckout, LocalDate dueDate) {
        this.dateOfCheckout = dateOfCheckout;
        this.dueDate = dueDate;
    }

    public LocalDate getDateOfCheckout() {
        return dateOfCheckout;
    }

    public void setDateOfCheckout(LocalDate dateOfCheckout) {
        this.dateOfCheckout = dateOfCheckout;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
