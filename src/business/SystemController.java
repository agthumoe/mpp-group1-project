package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import exceptions.LoginException;

public class SystemController implements ControllerInterface {
	public static ControllerInterface instance;
	public static Auth currentAuth = null;
	private final DataAccess dataAccess;

	private SystemController() {
		this.dataAccess = DataAccessFacade.getInstance();
	}

	public static synchronized ControllerInterface getInstance() {
		if (instance == null) {
			instance = new SystemController();
		}
		return instance;
	}
	
	public Auth login(String id, String password) throws LoginException {
		HashMap<String, User> map = this.dataAccess.getAllUsers();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
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
	public void checkout(String isbn, String memberId) {

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
}
