package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import ui.NewStart;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		if(currentAuth==Auth.LIBRARIAN) {
			NewStart.checkoutBook.setDisable(false);
			NewStart.searchEditMem.setDisable(false);
			NewStart.checkoutStatus.setDisable(false);
			NewStart.allMemeberId.setDisable(false);
			NewStart.login.setDisable(true);			
		}	
		
		if(currentAuth==Auth.ADMIN) {
			NewStart.addMember.setDisable(false);
			NewStart.addBookCopy.setDisable(false);
			NewStart.addBook.setDisable(false);
			NewStart.allBookId.setDisable(false);
			NewStart.login.setDisable(true);
		}
		
		if(currentAuth==Auth.BOTH) {
			NewStart.checkoutBook.setDisable(false);
			NewStart.addBook.setDisable(false);
			NewStart.addBookCopy.setDisable(false);
			NewStart.allBookId.setDisable(false);
			NewStart.addMember.setDisable(false);
			NewStart.searchEditMem.setDisable(false);
			NewStart.allMemeberId.setDisable(false);
			NewStart.login.setDisable(true);
			NewStart.checkoutStatus.setDisable(false);
		}
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	
	@Override
	public void addMember(LibraryMember librMem) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(librMem);
	}
	
	@Override
	public void addCopy(BookCopy bookCopy) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		da.saveNewBookCopy(bookCopy);
	}
	@Override
	public Book searchBookByIsbn(String isbn) {
		// TODO Auto-generated method stub
		
		return new DataAccessFacade().searchBookByIsbn(isbn);
	}
	@Override
	public void addBook(Book book) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
	}
	@Override
	public LibraryMember searchMemberById(String memId) {
		// TODO Auto-generated method stub
		 return new DataAccessFacade().searchMemberById(memId);
	}	
	//-------------------------------------------------------
		@Override
		public boolean checkoutBook(String memID, String isbn) {
			// TODO Auto-generated method stub
			DataAccess daf = new DataAccessFacade();
			LibraryMember member = daf.searchMemberById(memID);
			Book book = daf.searchBookByIsbn(isbn);
			BookCopy[] bk = book.getCopies();
			BookCopy Copy = null;
			boolean isAvailable = false;
			int nextAvailCopy, maxCheckoutLength = 0;
			for(int i=0; i<bk.length; i++) {
				isAvailable = bk[i].isAvailable();

				if(isAvailable == true) {
					nextAvailCopy = bk[i].getCopyNum();
					Copy = bk[i];
					break;
				}
			}
			maxCheckoutLength = book.getMaxCheckoutLength();
			LocalDate checkoutDate = LocalDate.now();
			LocalDate dueDate = checkoutDate.plusDays(maxCheckoutLength);
			CheckoutEntry checkoutEntry = new CheckoutEntry(checkoutDate, dueDate, Copy);
			CheckoutRecord checkoutRecord = member.getCheckoutRecord();
			checkoutRecord.addEntry(checkoutEntry);
			//member.setCheckoutRecord(checkoutRecord);
			daf.updateMember(member.getMemberId(),member);
			daf.updateBook(book.getIsbn(), book);
			if(member == null || book == null || isAvailable == false)
				return false;
			return true;
		}	
		
		public void addBookCopy(Book book, int copyNum)  {
			if(book.equals(null))
				System.out.println("The book was null");
			//Add each copy number to be added
			for(int i = 0;i<copyNum;i++) {
				book.addCopy();
			}
		}
		
}
