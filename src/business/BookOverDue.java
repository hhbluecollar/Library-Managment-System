package business;


import java.io.Serializable;
import java.time.LocalDate;

/* Immutable */
final public class BookOverDue implements Serializable {
	
	private static final long serialVersionUID = -891229800414574888L;
	

	private String bookIsbn;
	private String   bookTitle;
	private int copyNum;
	private String libraryMember;
	private LocalDate dueDate;
		

    public BookOverDue(String bookIsbn, String bookTitle, int copyNum, String libraryMember, LocalDate dueDate) {
		super();
		this.bookIsbn = bookIsbn;
		this.bookTitle = bookTitle;
		this.copyNum = copyNum;
		this.libraryMember = libraryMember;
		this.dueDate = dueDate;
	}

	public String getBookIsbn() {
		return bookIsbn;
	}

	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}


	public String getBookTitle() {
		return bookTitle;
	}


	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}


	public int getCopyNum() {
		return copyNum;
	}


	public void setCopyNum(int copyNum) {
		this.copyNum = copyNum;
	}


	public String  getMember() {
		return libraryMember;
	}


	public void setMember(String member) {
		this.libraryMember = member;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}


	@Override
    public String toString() {
        return  libraryMember + " "+ copyNum + " " + bookTitle + " " + dueDate  ;
    }
}
