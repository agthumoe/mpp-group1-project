package business;

import java.util.*;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class Main {

	public static void main(String[] args) {
		System.out.println(allWhoseZipContains3());
		System.out.println(allHavingOverdueBook());
		System.out.println(allHavingMultipleAuthors());

	}
	//Returns a list of all ids of LibraryMembers whose zipcode contains the digit 3
	public static List<String> allWhoseZipContains3() {
		DataAccess da = DataAccessFacade.getInstance();
		Collection<LibraryMember> members = da.getAllMembers().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		//implement
		return null;
		
	}
	//Returns a list of all ids of  LibraryMembers that have an overdue book
	public static List<String> allHavingOverdueBook() {
		DataAccess da = DataAccessFacade.getInstance();
		Collection<LibraryMember> members = da.getAllMembers().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		//implement
		return null;
		
	}
	
	//Returns a list of all isbns of  Books that have multiple authors
	public static List<String> allHavingMultipleAuthors() {
		DataAccess da = DataAccessFacade.getInstance();
		Collection<Book> books = da.getAllBooks().values();
		List<Book> bs = new ArrayList<>();
		bs.addAll(books);
		//implement
		return null;
		
		}

}
