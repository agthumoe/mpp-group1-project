package business;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.List;

public class CheckoutRecord implements Serializable {
    private static final long serialVersionUID = 7508481940058964471L;

    private List<RecordEntry> entries;
    private Member member;

    public CheckoutRecord(List<RecordEntry> entries, Member member) {
        this.entries = entries;
        this.member = member;
    }

    public List<RecordEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RecordEntry> entries) {
        this.entries = entries;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
