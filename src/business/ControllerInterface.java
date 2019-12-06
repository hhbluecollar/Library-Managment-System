package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	
	public void addMember(LibraryMember lib);
	public void addCopy(BookCopy bookCopy);
	public Book searchBookByIsbn(String isbn);
	public void addBook(Book book);
	public LibraryMember searchMemberById(String memId);
	
	public boolean checkoutBook(String memID, String isbn);
	public void addBookCopy(Book book, int copyNum);
}
