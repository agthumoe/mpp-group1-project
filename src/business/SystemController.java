package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import exceptions.LoginException;

public class SystemController implements ControllerInterface {
	public static ControllerInterface INSTANCE;
	public static Auth currentAuth = null;
	private final DataAccess dataAccess;

	private SystemController() {
		this.dataAccess = DataAccessFacade.getInstance();
	}

	public static synchronized ControllerInterface getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SystemController();
		}
		return INSTANCE;
	}
	
	public void login(String id, String password) throws LoginException {
		HashMap<String, User> map = this.dataAccess.getAllUsers();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
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
	public void checkout(Book book, LibraryMember member) {

	}

	@Override
	public void makeCopy(Book book) {
		book.addCopy();

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
