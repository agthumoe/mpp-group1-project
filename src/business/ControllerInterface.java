package business;

import dataaccess.Auth;
import exceptions.LoginException;

import java.util.List;

public interface ControllerInterface {
    Auth login(String id, String password) throws LoginException;

    void addMember(LibraryMember member);

    void addBook(Book book);

    Book checkout(String isbn, String memberId);

    void makeCopy(Book book, int numberOfCopies);

    List<LibraryMember> getAllMembers();

    List<Book> getAllBooks();

    List<String> allMemberIds();

    List<String> allBookIds();

    List<Author> getAllAuthors();

    void addAuthor(Author author);

    List<CheckoutRecord> getAllCheckoutRecords();
}
