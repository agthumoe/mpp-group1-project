package business;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import exceptions.BookNotFoundException;
import exceptions.LoginException;
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
        LibraryMember member = this.dataAccess.getLibraryMember(memberId);
        Book book = this.dataAccess.getBook(isbn);
        Optional<BookCopy> copy = book.getNextAvailableCopy();
        if (copy.isEmpty()) {
            throw new BookNotFoundException("Book copy is not available");
        }
        CheckoutRecord record = new CheckoutRecord(Util.getRandom(), member, new RecordEntry(copy.get()));
        copy.get().updateCopyAvailability();
        dataAccess.saveNewRecord(record);
        dataAccess.updateBook(book);
        dataAccess.updateMember(member);
        return book;

    }

    @Override
    public void makeCopy(Book book, int numberOfCopies) {
        for (int i = 0; i < numberOfCopies; i++) {
            book.addCopy();
        }
        this.dataAccess.saveBook(book);
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
