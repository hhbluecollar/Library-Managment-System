package business;

import java.io.Serializable;
import java.time.LocalDate;

final public class CheckoutEntry  implements Serializable {

	private static final long serialVersionUID = 2169546181276641090L;
	private LocalDate checkoutdate;
	private LocalDate dueDate;
	private BookCopy bookCopy;
	
	 CheckoutEntry(LocalDate checkoutdate, LocalDate dueDate, BookCopy copy) {
		this.checkoutdate = checkoutdate;
		this.dueDate = dueDate;
		bookCopy = copy;
	}

	public LocalDate getCheckoutdate() {
		return checkoutdate;
	}

	public void setCheckoutdate(LocalDate checkoutdate) {
		this.checkoutdate = checkoutdate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}
	
	
}
