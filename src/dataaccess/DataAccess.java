package dataaccess;

import java.util.HashMap;

import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;

public interface DataAccess { 
	HashMap<String,Book> getAllBooks();
	void saveBook(Book book);
	void updateBook(Book book);
	void deleteBook(Book book);

	HashMap<String,User> getAllUsers();
	void saveUser(User user);
	void updateUser(User user);
	void deleteUser(User user);

	HashMap<String, LibraryMember> getAllMembers();
	void saveMember(LibraryMember member);
	void updateMember(LibraryMember member);
	void deleteMember(LibraryMember member);

	HashMap<String,CheckoutRecord> getAllCheckoutRecords();
	void saveCheckout(CheckoutRecord checkoutRecord);
	void updateCheckout(CheckoutRecord checkoutRecord);
}
