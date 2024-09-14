package business;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import exceptions.BookNotFoundException;
import exceptions.LoginException;
import exceptions.MemberNotFoundException;
import ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SystemController implements ControllerInterface {
    public static ControllerInterface instance;
    private static Auth currentAuth = null;
    private final DataAccess dataAccess;

    private SystemController() {
        this.dataAccess = DataAccessFacade.getInstance();
    }

    public static Auth getCurrentAuth() {
        return currentAuth;
    }

    public static void setCurrentAuth(Auth currentAuth) {
        SystemController.currentAuth = currentAuth;
        LibrarySystem.getInstance().updateAuth(currentAuth);
        AddMemberWindow.getInstance().updateAuth(currentAuth);
        AllMembersWindow.getInstance().updateAuth(currentAuth);
        AddBookWindow.getInstance().updateAuth(currentAuth);
        AllBooksWindow.getInstance().updateAuth(currentAuth);
        AddCheckoutsWindow.getInstance().updateAuth(currentAuth);
        AllCheckoutRecordWindow.getInstance().updateAuth(currentAuth);
    }

    public static synchronized ControllerInterface getInstance() {
        if (instance == null) {
            instance = new SystemController();
        }
        return instance;
    }

    public Auth login(String id, String password) throws LoginException {
        HashMap<String, User> map = this.dataAccess.getAllUsers();
        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        return map.get(id).getAuthorization();
    }

    @Override
    public void addMember(LibraryMember member) {
        this.dataAccess.saveMember(member);
    }

    @Override
    public void addBook(Book book) {
        this.dataAccess.saveBook(book);
    }

    @Override
    public Book checkout(String isbn, String memberId) {
        HashMap<String, LibraryMember> members = this.dataAccess.getAllMembers();
        HashMap<String, Book> books = this.dataAccess.getAllBooks();
        if (!members.containsKey(memberId)) {
            throw new MemberNotFoundException("Member ID " + memberId + " not found");
        }
        if (!books.containsKey(isbn)) {
            throw new BookNotFoundException("Book ID " + isbn + " not found");
        }

        Book book = books.get(isbn);
        if (!book.isAvailable()) {
            throw new BookNotFoundException("Book not found");
        }
        Optional<BookCopy> copy = book.getNextAvailableCopy();
        if (copy.isEmpty()) {
            throw new BookNotFoundException("Book copy not available");
        }
        LibraryMember member = members.get(memberId);

        copy.get().changeAvailability();
        CheckoutRecord record = new CheckoutRecord(Util.getRandom(), member, new RecordEntry(copy.get()));
        dataAccess.saveNewRecord(record);
        dataAccess.updateBook(book);
        dataAccess.saveNewMember(member);
        return book;

    }

    @Override
    public void makeCopy(Book book) {
        book.addCopy();

    }

    @Override
    public List<LibraryMember> getAllMembers() {
        return this.dataAccess.getAllMembers().values().stream().toList();
    }

    @Override
    public List<Book> getAllBooks() {
        return this.dataAccess.getAllBooks().values().stream().toList();
    }

    @Override
    public List<String> allMemberIds() {
        List<String> retval = new ArrayList<>();
        retval.addAll(this.dataAccess.getAllMembers().keySet());
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        List<String> retval = new ArrayList<>();
        retval.addAll(this.dataAccess.getAllBooks().keySet());
        return retval;
    }

    @Override
    public List<Author> getAllAuthors() {
        return this.dataAccess.getAllAuthors().values().stream().toList();
    }

    @Override
    public void addAuthor(Author author) {
        this.dataAccess.saveAuthor(author);
    }

    @Override
    public List<CheckoutRecord> getAllCheckoutRecords() {
        return this.dataAccess.getAllCheckoutRecords().values().stream().toList();
    }
}
