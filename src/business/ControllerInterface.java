package business;

import java.lang.reflect.Member;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	void login(String id, String password) throws LoginException;
	void addMember(LibraryMember member);
	void addBook(Book book);
	void checkout(Book book, Member member);
	void makeCopy(Book book);
	List<String> allMemberIds();
	List<String> allBookIds();

}
