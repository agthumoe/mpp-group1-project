package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {
    private static final long serialVersionUID = 7508481940058964471L;

    private String recordID;

    private List<RecordEntry> entries;

    private LibraryMember member;

    public CheckoutRecord() {
        this.entries = new ArrayList<RecordEntry>();
    }

    public CheckoutRecord(String recordID, LibraryMember member) {
        this.recordID = recordID;
        this.setMember(member);
        this.entries = new ArrayList<>();
    }

    public CheckoutRecord(String recordID, LibraryMember member, RecordEntry entry) {
        this.recordID = recordID;
        this.entries = new ArrayList<>();
        this.entries.add(entry);
        this.setMember(member);
    }

    public String getRecordID() {
        return recordID;
    }

    public void addRecordEntry(RecordEntry recordEntry) {
        entries.add(recordEntry);
    }

    public List<RecordEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RecordEntry> entries) {
        this.entries = entries;
    }

    public LibraryMember getMember() {
        return member;
    }

    public void setMember(LibraryMember member) {
        this.member = member;
        this.member.addCheckoutRecord(this);
    }
}
