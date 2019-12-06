package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final public class CheckoutRecord  implements Serializable{

	private static final long serialVersionUID = -966150412315735623L;
	private List<CheckoutEntry> checkoutEntry;// = new ArrayList<>();
	
	public CheckoutRecord() { 
		checkoutEntry = new ArrayList<>();
	}
	
	public void addEntry(CheckoutEntry entry) {
		checkoutEntry.add(entry);
	}

	public List<CheckoutEntry> getCheckoutEntry() {
		return checkoutEntry;
	}

	public void setCheckoutEntry(List<CheckoutEntry> checkoutEntry) {
		this.checkoutEntry = checkoutEntry;
	}
	
	
}
