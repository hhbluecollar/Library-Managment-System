package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JComboBox.KeySelectionManager;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;
import com.sun.javafx.collections.MappingChange.Map;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import ui.NewStart;
import ui.WelcomeWindow;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("The username " + id + " not found.");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Incorrect password.");
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
		
 	    WelcomeWindow.INSTANCE.init();
 	    NewStart.rightContainer.add(WelcomeWindow.getGrid(),0,0);
 	    NewStart.topContainer.setCenter( NewStart.rightContainer);
		
	}
	@Override
	public HashMap<String, LibraryMember> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> memHashMap = da.readMemberMap();
		return memHashMap;
	}
	
	@Override
	public HashMap<String, Book> allBookIds() {
		DataAccess da = new DataAccessFacade();		
		HashMap<String, Book> booksHashMap = da.readBooksMap();
		return 	booksHashMap;
	}
	
	@Override
	public void addMember(LibraryMember librMem, boolean addType) throws LibrarySystemException {
		//check if any field is empty
		//check of telephone no is an integer
		
		if( librMem.getMemberId().isEmpty() ||
			librMem.getFirstName().isEmpty()      ||
			librMem.getLastName().isEmpty()       ||
			librMem.getTelephone().trim().isEmpty()      ||
			librMem.getAddress().getState().trim().isEmpty() ||
			librMem.getAddress().getStreet().isEmpty()||
			librMem.getAddress().getCity().isEmpty()  ||
			librMem.getAddress().getZip().isEmpty()) {
			throw new LibrarySystemException("All fields must be provided.");
		}
		
		try {
			Integer.parseInt(librMem.getTelephone());
		}catch(NumberFormatException e){
			throw new LibrarySystemException("Telephone number must be numeric.");

		}
		DataAccess da = new DataAccessFacade();
		if(addType==true)
			da.saveNewMember(librMem);
		da.updateMember(librMem.getMemberId(), librMem);
	}
	
	@Override
	public void addCopy(BookCopy bookCopy) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		da.saveNewBookCopy(bookCopy);
	}
	@Override
	public Book searchBookByIsbn(String isbn) throws LibrarySystemException {
		Book book = new DataAccessFacade().searchBookByIsbn(isbn);
		if(book==null)
			throw new LibrarySystemException("The book with ISBN "+isbn+" is not found.");
		
		return book ;
	}
	@Override
	public void addBook(Book book) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		
		//if the book is already added notify
		/* TO DO!!
		//Lets check the fields are there
		if(book.getAuthors().size()==0)
			throw new LibrarySystemException("Please provide at least one author.");

		if(!book.getIsbn().isEmpty()||!book.getTitle().isEmpty()){
			System.out.println("the authors no is : "+book.getAuthors().size());
			for (Author a: book.getAuthors()) {
				if(a.getAddress().getState().isEmpty() || a.getAddress().getCity().isEmpty() ||
				   a.getAddress().getStreet().isEmpty() || a.getAddress().getZip().isEmpty()  ||
				   a.getFirstName().isEmpty() || a.getLastName().isEmpty() ||a.getTelephone().isEmpty() ||
				   a.getBio().isEmpty()) {
					
					throw new LibrarySystemException("All fields for author must be provided.");
				}
				try {
					Integer.parseInt(a.getTelephone());
				} catch (NumberFormatException e) {
					throw new LibrarySystemException("The telephone digits must be integers.");
				}
				try {
					Integer.parseInt(a.getAddress().getZip());
				} catch (NumberFormatException e) {
					throw new LibrarySystemException("Zip format incorrect.");
				}
			}
	   }
		else
			throw new LibrarySystemException("All book fields must be provided.");
		*/
		da.saveNewBook(book);
	}
	@Override
	public LibraryMember searchMemberById(String memId) throws LibrarySystemException {
		if(memId.isEmpty())
			throw new LibrarySystemException("Member id should be provided.");
		LibraryMember member = new DataAccessFacade().searchMemberById(memId);
		if(member==null)
			throw new LibrarySystemException("Memeber with id "+memId+" is not found.");

		return member ;
	}	
	
	public String checkoutStatus(String memberId) throws LibrarySystemException {
		LibraryMember member = searchMemberById(memberId);

		List<CheckoutEntry> checkOuts = member.getCheckoutRecord().getCheckoutEntry();

		StringBuilder sb = new StringBuilder();
		int bkNo =0;
		sb.append("The checkout records for "+member.getLastName()+" "+member.getFirstName()+" are: \n");
		System.out.println(checkOuts.size());
		for(CheckoutEntry s: checkOuts) {
			bkNo++;
			sb.append(bkNo+". "+s.getBookCopy().getBook().getTitle()+"\n");
			sb.append("\t\t"+"Checkout Date: "+s.getCheckoutdate()+"\n");
			sb.append("\t\t" +"Due date: "+s.getDueDate()+ "\n");
			sb.append("\t\t" +"Max checkout length: "+s.getBookCopy().getBook().getMaxCheckoutLength()+ "\n");
		}
		return sb.toString();
	}
	
		@Override
		public void checkoutBook(String memID, String isbn) throws LibrarySystemException {
			
			if(memID.isEmpty()||isbn.isEmpty())
				throw new LibrarySystemException("Both member id and ISBN must be provided.");
			
			if(!isbn.matches("[0-9-]*"))
	
		        throw new LibrarySystemException("The ISBN format is not correct number.");	

			DataAccess daf = new DataAccessFacade();
			LibraryMember member = daf.searchMemberById(memID);
			if(member==null)
				throw new LibrarySystemException("The member id "+memID+" is not found.");
			Book book = daf.searchBookByIsbn(isbn);
			if(book==null)
				throw new LibrarySystemException("The isbn "+isbn+" is not found.");
						
			BookCopy[] bk = book.getCopies();
			BookCopy copy = null;
			boolean isAvailable = false;
			int  maxCheckoutLength = 0;
			//check for any available copy 
			for(int i=0; i<bk.length; i++) {
				isAvailable = bk[i].isAvailable();

				if(isAvailable == true) {
					copy = bk[i];
					break;
				}
			}
			if(copy==null)
				throw new LibrarySystemException("There is not available copy for this book");
			maxCheckoutLength = book.getMaxCheckoutLength();
			LocalDate checkoutDate = LocalDate.now();
			LocalDate dueDate = checkoutDate.plusDays(maxCheckoutLength);
			member.checkout(copy, checkoutDate, dueDate);			
			daf.updateMember(member.getMemberId(),member);
			daf.updateBook(book.getIsbn(), book);
		}	
		
		public void addBookCopy(String isbn, String copyNum) throws LibrarySystemException  {

			if(copyNum.isEmpty() || isbn.isEmpty() )
				throw new LibrarySystemException("All fields should be filled.");
			try {
				
						isbn.matches("^[0-9-]+$");
					
				}catch (NumberFormatException ex) {
					throw new LibrarySystemException("The ISBN format is not correct number.");	
				}
			try {
				Integer.parseInt(copyNum);
			
			}catch (NumberFormatException ex) {
				throw new LibrarySystemException("The number of copy must be integer value.");	
			}
			//Add each copy number to be added
			Book book;
			try{
				 book = searchBookByIsbn(isbn);		
			
				 for(int i = 0;i<Integer.parseInt(copyNum);i++) {					
					 book.addCopy();				
				 }
			}catch(LibrarySystemException ex) {
				throw new LibrarySystemException("The book was not found.");
			}
			BookCopy bookCopy = new  BookCopy(book, Integer.parseInt(copyNum),true);
			DataAccess da = new DataAccessFacade();
			da.saveNewBookCopy(bookCopy);
		}	
		
		
		public void overdueCheck(String isbn) throws LibrarySystemException {
			Book book = searchBookByIsbn(isbn);
			List<Book> checkoutBookList = new ArrayList<>();

			for(BookCopy bookCopy: book.getCopies()) {
				if(bookCopy.isAvailable()==false)
					checkoutBookList.add(book);
			}
			
		}
		
		//lists all over due books /*** to be done ***/
		public List<String> overDueBooks(){
		
			DataAccess da = new DataAccessFacade();
			HashMap<String, Book> booksHashMap = da.readBooksMap();
			List<Book> bookList = new ArrayList<>();
			bookList.addAll(booksHashMap.values());
			List<Book> checkedoutBookList = new ArrayList<>();
			
			for(Book book: bookList) {
				for(BookCopy bookCopy: book.getCopies()) {
					if(bookCopy.isAvailable()==false)
						checkedoutBookList.add(book);
				}
			}
			
			
			
			HashMap<String, LibraryMember> memeberHashMap = da.readMemberMap();
			List<LibraryMember> memeberList = new ArrayList<>();
			memeberList.addAll(memeberHashMap.values());
			List<LibraryMember> memWithOverDueBookList = new ArrayList<>();
			
			for(LibraryMember mem:memeberList) {
				for(CheckoutEntry entry: mem.getCheckoutRecord().getCheckoutEntry()) {
					if(entry.getDueDate().isBefore(LocalDate.now())) {
						memWithOverDueBookList.add(mem);
					}
				}					
			}
			
			for (LibraryMember libraryMember : memWithOverDueBookList) {
				// TO DO(user stream)
			}
						
			return null;
		}
}
