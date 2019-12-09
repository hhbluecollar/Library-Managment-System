package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.CopyOverDue;
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
			NewStart.overDueCheck.setDisable(false);
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
			NewStart.overDueCheck.setDisable(false);

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
		
			if(!librMem.getTelephone().trim().matches("[0-9-]*"))
				throw new LibrarySystemException("Telephone format is not correct.");

		DataAccess da = new DataAccessFacade();
		if(addType==true)
			da.saveNewMember(librMem);
		da.updateMember(librMem.getMemberId(), librMem);
	}
	
//	@Override
//	public void addCopy(BookCopy bookCopy) {
//		// TODO Auto-generated method stub
//		DataAccess da = new DataAccessFacade();
//		da.saveNewBookCopy(bookCopy);
//	}
	@Override
	public Book searchBookByIsbn(String isbn) throws LibrarySystemException {
		Book book = new DataAccessFacade().searchBookByIsbn(isbn);
		if(book==null)
			throw new LibrarySystemException("The book with ISBN "+isbn+" is not found.");
		
		return book ;
	}
	
	@Override
	public boolean searchBookByIsbnIsAvaialable(String isbn) {
		Book book = new DataAccessFacade().searchBookByIsbn(isbn);
		if(book!=null)
			return true;
		return false;
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
	
	
	@Override
	public boolean searchMemberByIdIsAvaialable(String memId)  {
		LibraryMember member = new DataAccessFacade().searchMemberById(memId);
		if(member!=null)
			return true;
		return false;
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
				throw new LibrarySystemException("All fields should be provided.");
				
			if(!isbn.matches("[0-9-]*"))
					
				throw new LibrarySystemException("The ISBN format is not correct.");	
			
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
			//BookCopy bookCopy = new  BookCopy(book, Integer.parseInt(copyNum),true);
			DataAccess da = new DataAccessFacade();
			da.saveNewBookCopy(book);
		}	
		
		
		@SuppressWarnings("unchecked")
		public void  overdueCheck(String isbn) throws LibrarySystemException {
						
			DataAccess da = new DataAccessFacade();			 
			Book book = searchBookByIsbn(isbn);
			
			HashMap<String, LibraryMember> memeberHashMap = da.readMemberMap();
			List<BookOverDue> overDueList =  new ArrayList<BookOverDue>();
			List<LibraryMember> memeberList = new ArrayList<LibraryMember>();
			
			memeberList.addAll(memeberHashMap.values());
			
			for(@SuppressWarnings("unused") BookCopy bookCopy: book.getCopies()) {
				for(LibraryMember mem:memeberList) {
					for(CheckoutEntry entry: mem.getCheckoutRecord().getCheckoutEntry()) {
						if(entry.getDueDate().isBefore(LocalDate.now()) && entry.getBookCopy().getBook().equals(book)) {
							
							overDueList.add(new BookOverDue(
															book.getIsbn(),book.getTitle(),
															entry.getBookCopy().getCopyNum(),
															mem.getFirstName()+" " + mem.getLastName(),
															entry.getDueDate()
															));
						}
						else
							System.out.println("No record found");
					}					
				}				
			}
			
			//********************************************************
			
			TableView<String> table = new TableView<String>();
		    TableView<BookOverDue> overDueTableView = new TableView<>();

			TableColumn<BookOverDue,String> isbnCol = new TableColumn<>("ISBN");
			 TableColumn<BookOverDue,String> bookTitleCol = new TableColumn<>("Title");
			 TableColumn<BookOverDue,Integer> copyNumCol = new TableColumn<>("Copy No.");
			 TableColumn<BookOverDue,String> memeberCol = new TableColumn<>("Member Name");
			 TableColumn<BookOverDue,LocalDate > duedateeCol = new TableColumn<>("Due Date.");

			 isbnCol.setCellValueFactory(new PropertyValueFactory<>("bookIsbn"));
			 bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));		
			 copyNumCol.setCellValueFactory(new PropertyValueFactory<>("copyNum"));		
			 memeberCol.setCellValueFactory(new PropertyValueFactory<>("libraryMember"));		
			 duedateeCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));	

			 overDueTableView.getColumns().setAll(isbnCol, bookTitleCol, copyNumCol, memeberCol,duedateeCol);
			 CopyOverDue.booksTableView.getColumns().setAll(isbnCol, bookTitleCol, copyNumCol, memeberCol,duedateeCol);
		     @SuppressWarnings("rawtypes")
			ObservableList data = FXCollections.observableList(overDueList);
		     overDueTableView.setItems(data);
			 
			 isbnCol.setSortType(TableColumn.SortType.DESCENDING);
			 
			 TableView<Book>  bookTable = new TableView<>();		
			 TableView<LibraryMember>  memberTable = new TableView<>();
			 CopyOverDue.getGrid().add(overDueTableView,  0, 2,5,1);
			 CopyOverDue.booksTableView.setItems(data);
					
			//********************************************************
		}				
		
		
		
		//lists all over due books /*** to be done ***/
		public List<String> allOverDueBooks(){
		
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
		@Override
		public List<String> overDueBooks() {
			// TODO Auto-generated method stub
			return null;
		}
}
