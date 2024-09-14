package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final public class LibraryMember extends Person implements Serializable {
    private static final long serialVersionUID = -2226197306790714013L;

    private String memberId;
    private List<CheckoutRecord> checkoutRecords;

    public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
        super(fname, lname, tel, add);
        this.memberId = memberId;
        this.checkoutRecords = new ArrayList<>();
    }

    public List<CheckoutRecord> getCheckoutRecord() {
        return checkoutRecords;
    }

    public void addCheckoutRecord(CheckoutRecord checkoutRecord) {
        this.checkoutRecords.add(checkoutRecord);
    }

    public String getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "LibraryMember{" +
                "memberId='" + memberId + '\'' +
                ", checkoutRecord=" + checkoutRecords +
                '}';
    }
}
