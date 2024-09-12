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
	public void checkout(String isbn, String memberId) {
		/*
		If both member ID and book ID are found and a copy is available,
		a new checkout record entry is created,
		containing the copy of the requested book and the checkout date and due date.
		 */

		/*
		member.getMemberId();
		book.getIsbn();
		boolean b = book.getCopies().length == 0;

		// Member or book not found

		BookCopy availableCopy = book.isAvailable()
		if (availableCopy == null) {

		}

		String checkoutDate = // todaydate
		String dueDate = // 7 or 21 days
		CheckoutEntry entry = new CheckoutEntry(availableCopy, checkoutDate, dueDate);
		member.getCheckoutRecord().addEntry(entry);
		availableCopy.setAvailable(false);

		dataAccess.saveNewMember(member);
		dataAccess.saveNewBook(book);
		*/
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
