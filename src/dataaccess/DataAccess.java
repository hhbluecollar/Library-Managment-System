package dataaccess;

import java.util.HashMap;
import business.Book;
import business.BookCopy;
import business.LibraryMember;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member); 
	public void saveNewBookCopy(BookCopy bookCopy);
	public Book searchBookByIsbn(String isbn);
	public void saveNewBook(Book book);
	public LibraryMember searchMemberById(String memId); 
	
	//-------------------------------------------
		//public LibraryMember searchMemberbyID(String memID);
		public void updateBook(String isbn, Book book);
		public void updateMember(String memID, LibraryMember member);
		//-------------------------------------------
}
