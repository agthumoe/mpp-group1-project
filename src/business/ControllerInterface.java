package business;

import exceptions.LoginException;

import java.util.List;

public interface ControllerInterface {
	void login(String id, String password) throws LoginException;
	void addMember(LibraryMember member);
	void addBook(Book book);
	Book checkout(String isbn, String memberId);
	void makeCopy(Book book);
	List<String> allMemberIds();
	List<String> allBookIds();

}
