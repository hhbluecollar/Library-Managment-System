package business;

import java.util.HashMap;
import java.util.List;
import business.Book;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public HashMap<String, LibraryMember> allMemberIds();
	public HashMap<String, Book> allBookIds();
	
	public void addMember(LibraryMember lib, boolean type) throws LibrarySystemException;
	//public void addCopy(BookCopy bookCopy);
	public Book searchBookByIsbn(String isbn) throws LibrarySystemException;
	public void addBook(Book book) throws LibrarySystemException;
	public LibraryMember searchMemberById(String memId) throws LibrarySystemException;
	
	public void checkoutBook(String memID, String isbn) throws LibrarySystemException;
	public void addBookCopy(String isbn, String copyNum) throws LibrarySystemException;
	public String checkoutStatus(String memberId) throws LibrarySystemException;
	public List<String> overDueBooks();
	public void overdueCheck(String isbn) throws LibrarySystemException;
	public boolean searchBookByIsbnIsAvaialable(String isbn);
	public boolean searchMemberByIdIsAvaialable(String memId);

}
