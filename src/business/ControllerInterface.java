package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	
	public void addMember(LibraryMember lib, boolean type) throws LibrarySystemException;
	public void addCopy(BookCopy bookCopy);
	public Book searchBookByIsbn(String isbn) throws LibrarySystemException;
	public void addBook(Book book) throws LibrarySystemException;
	public LibraryMember searchMemberById(String memId) throws LibrarySystemException;
	
	public void checkoutBook(String memID, String isbn) throws LibrarySystemException;
	public void addBookCopy(String isbn, String copyNum) throws LibrarySystemException;
	public String checkoutStatus(String memberId) throws LibrarySystemException;
}
