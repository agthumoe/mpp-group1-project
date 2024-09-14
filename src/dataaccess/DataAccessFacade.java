package dataaccess;

import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;
import exceptions.BookNotFoundException;
import exceptions.MemberNotFoundException;
import exceptions.UserNotFoundException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class DataAccessFacade implements DataAccess {
    private static DataAccess instance = null;

    private DataAccessFacade() {
    }

    public static synchronized DataAccess getInstance() {
        if (instance == null) {
            instance = new DataAccessFacade();
        }
        return instance;
    }

    public static final String OUTPUT_DIR = System.getProperty("user.dir")
            + "/src/dataaccess/storage";

    public static final String DATE_PATTERN = "MM/dd/yyyy";

    public void saveMember(LibraryMember member) {
        HashMap<String, LibraryMember> members = getAllMembers();
        String memberId = member.getMemberId();
        members.put(memberId, member);
        saveToStorage(StorageType.MEMBERS, members);
    }

    @Override
    public void updateMember(LibraryMember member) {
        HashMap<String, LibraryMember> members = getAllMembers();
        if (members.containsKey(member.getMemberId())) {
            members.put(member.getMemberId(), member);
        } else {
            throw new MemberNotFoundException("Member Id: " + member.getMemberId() + ", cannot be found.");
        }
        saveToStorage(StorageType.MEMBERS, member);

    }

    @Override
    public void deleteMember(LibraryMember member) {
        HashMap<String, LibraryMember> members = getAllMembers();
        String memberId = member.getMemberId();
        if (members.containsKey(memberId)) {
            members.remove(memberId);
        } else {
            throw new MemberNotFoundException("Member Id: " + member.getMemberId() + ", cannot be found.");
        }
        saveToStorage(StorageType.MEMBERS, member);
    }

    @Override
    public HashMap<String, CheckoutRecord> getAllCheckoutRecords() {
        return null;
    }

    @Override
    public void saveBook(Book book) {
        HashMap<String, Book> books = getAllBooks();
        String isbn = book.getIsbn();
        books.put(isbn, book);
        saveToStorage(StorageType.BOOKS, books);
    }

    @Override
    public void updateBook(Book book) throws BookNotFoundException {
        HashMap<String, Book> books = getAllBooks();
        if (books.containsKey(book.getIsbn())) {
            books.put(book.getIsbn(), book);
        } else {
            throw new BookNotFoundException("Book: " + book.getIsbn() + ", cannot be found.");
        }
        saveToStorage(StorageType.BOOKS, books);
    }

    public void saveNewRecord(CheckoutRecord record) {
        HashMap<String, CheckoutRecord> recordsMap = readRecordsMap();
        String recordID = record.getRecordID();
        recordsMap.put(recordID, record);
        saveToStorage(StorageType.RECORDS, recordsMap);
    }

    public HashMap<String, CheckoutRecord> readRecordsMap() {

        return (HashMap<String, CheckoutRecord>) readFromStorages(StorageType.RECORDS);
    }

    //implement: other save operations
    public void saveNewMember(LibraryMember member) {
        HashMap<String, LibraryMember> mems = getAllMembers();
        String memberId = member.getMemberId();
        mems.put(memberId, member);
        saveToStorage(StorageType.MEMBERS, mems);
    }

    @Override
    public void deleteBook(Book book) {
        HashMap<String, Book> books = getAllBooks();
        if (books.containsKey(book.getIsbn())) {
            books.remove(book.getIsbn());
        } else {
            throw new BookNotFoundException("Book: " + book.getIsbn() + ", cannot be found.");
        }
        saveToStorage(StorageType.BOOKS, books);
    }

    @Override
    public void saveCheckout(CheckoutRecord checkoutRecord) {

    }

    @Override
    public void updateCheckout(CheckoutRecord checkoutRecord) {

    }

    public HashMap<String, Book> getAllBooks() {
        return readFromStorage(StorageType.BOOKS);
    }

    public HashMap<String, LibraryMember> getAllMembers() {
        return readFromStorage(StorageType.MEMBERS);
    }


    public HashMap<String, User> getAllUsers() {
        return readFromStorage(StorageType.USERS);
    }

    @Override
    public void saveUser(User user) {
        HashMap<String, User> users = getAllUsers();
        users.put(user.getId(), user);
        saveToStorage(StorageType.USERS, users);
    }


    @Override
    public void updateUser(User user) {
        HashMap<String, User> users = getAllUsers();
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new UserNotFoundException("User: " + user.getId() + ", cannot be found.");
        }
        saveToStorage(StorageType.USERS, users);

    }

    @Override
    public void deleteUser(User user) {
        HashMap<String, User> users = getAllUsers();
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
        } else {
            throw new UserNotFoundException("User: " + user.getId() + ", cannot be found.");
        }
        saveToStorage(StorageType.USERS, users);

    }


    /////load methods - these place test data into the storage area
    ///// - used just once at startup


    static void loadBookMap(List<Book> bookList) {
        HashMap<String, Book> books = new HashMap<String, Book>();
        bookList.forEach(book -> books.put(book.getIsbn(), book));
        saveToStorage(StorageType.BOOKS, books);
    }

    static void loadUserMap(List<User> userList) {
        HashMap<String, User> users = new HashMap<String, User>();
        userList.forEach(user -> users.put(user.getId(), user));
        saveToStorage(StorageType.USERS, users);
    }

    static void loadMemberMap(List<LibraryMember> memberList) {
        HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
        memberList.forEach(member -> members.put(member.getMemberId(), member));
        saveToStorage(StorageType.MEMBERS, members);
    }
    @Override
    public void loadRecordMap(List<CheckoutRecord> authorList) {
        HashMap<String, CheckoutRecord> authors = new HashMap<String, CheckoutRecord>();
        authorList.forEach(author -> authors.put(author.getRecordID(), author));
        saveToStorage(StorageType.RECORDS, authors);
    }

    public static Object readFromStorages(StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Files.createDirectories(FileSystems.getDefault().getPath(OUTPUT_DIR));
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            if (!Files.exists(path)) {
                TestData.initData();
            }
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }

    static void saveToStorage(StorageType type, Object ob) {
        ObjectOutputStream out = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(ob);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> HashMap<K, V> readFromStorage(StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return (HashMap<K, V>) retVal;
    }


    final static class Pair<S, T> implements Serializable {

        S first;
        T second;

        Pair(S s, T t) {
            first = s;
            second = t;
        }

        @Override
        public boolean equals(Object ob) {
            if (ob == null) return false;
            if (this == ob) return true;
            if (ob.getClass() != getClass()) return false;
            @SuppressWarnings("unchecked")
            Pair<S, T> p = (Pair<S, T>) ob;
            return p.first.equals(first) && p.second.equals(second);
        }

        @Override
        public int hashCode() {
            return first.hashCode() + 5 * second.hashCode();
        }

        @Override
        public String toString() {
            return "(" + first.toString() + ", " + second.toString() + ")";
        }

        private static final long serialVersionUID = 5399827794066637059L;
    }

}
