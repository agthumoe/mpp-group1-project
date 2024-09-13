package business;

import dataaccess.Auth;
import exceptions.LoginException;

import java.util.List;
import java.util.Map;

public interface ControllerInterface {
	Auth login(String id, String password) throws LoginException;
	void addMember(LibraryMember member);
	void addBook(Book book);
	Book checkout(String isbn, String memberId);
	void makeCopy(Book book);
	List<LibraryMember> getAllMembers();
	List<String> allMemberIds();
	List<String> allBookIds();

}
