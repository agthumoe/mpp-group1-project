package dataaccess;

import business.Author;
import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface DataAccess {
    HashMap<String, Book> getAllBooks();

    void saveBook(Book book);

    void updateBook(Book book);

    void deleteBook(Book book);

    Book getBook(String isbn);

    LibraryMember getLibraryMember(String memberId);

    HashMap<String, User> getAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    HashMap<String, LibraryMember> getAllMembers();

    void saveMember(LibraryMember member);

    void updateMember(LibraryMember member);

    void deleteMember(LibraryMember member);

    HashMap<String, CheckoutRecord> getAllCheckoutRecords();

    void saveCheckout(CheckoutRecord checkoutRecord);

    void updateCheckout(CheckoutRecord checkoutRecord);

    public void saveNewRecord(CheckoutRecord record);

    public void saveNewMember(LibraryMember member);

    public void loadRecordMap(List<CheckoutRecord> authorList);

    HashMap<String, Author> getAllAuthors();

    void saveAuthor(Author author);
}
