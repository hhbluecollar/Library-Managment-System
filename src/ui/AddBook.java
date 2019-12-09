package ui;

import java.util.ArrayList;
import java.util.List;
import business.Address;
import business.Author;
import business.Book;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddBook extends Stage implements LibWindow {
	public static final AddBook INSTANCE = new AddBook();
	 
 
     TextField fNameField;
     TextField lNameField;
     TextField telephoneField;
     TextArea  bioTextArea;
     
     TextField streetTextField;
     TextField cityTextField ;
     TextField statetTextField;
     TextField zipTextField ;
     
     
		public void clearTextFields() {
		      fNameField.clear();;
		      lNameField.clear();
		      telephoneField.clear();
		      bioTextArea.clear();
		     
		      streetTextField.clear();
		      cityTextField.clear() ;
		      statetTextField.clear();
		      zipTextField.clear() ;	
		}
    private List<Author> authors = new ArrayList<>();
	
    private static GridPane grid;	
	public static GridPane getGrid() {
		return grid;
	}
	private boolean isInitialized = false;
	
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private Text messageBar = new Text();
	public void clear() {
		messageBar.setText("");
	}
	
	/* This class is a singleton */
    private AddBook () {}
    
	public void init() { 
        grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add Book");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);

        //*****************test area *****************
        //Add the authors

        TextField isbnTextField;
        TextField titleTextField ;
        ComboBox<Integer> maxCheckoutComboBox;    
        VBox bookPane = new VBox( 10 );
        bookPane.getChildren().addAll(
                row( "ISBN:", isbnTextField = new TextField() ),
                row( "Title:", titleTextField = new TextField() ),
                row( "Max Checkout Length:", maxCheckoutComboBox = new ComboBox<>() )                
        );
        //Initialize the items and select a default value 
        maxCheckoutComboBox.getItems().addAll(7,21);
        maxCheckoutComboBox.setValue(7);

        VBox authorPpane = new VBox( 10 );
        authorPpane.getChildren().addAll(       

                row( "First Name:", fNameField = new TextField() ),
                row( "Last Name:", lNameField = new TextField() ),
                row( "Telephone No:", telephoneField = new TextField()),
                row( "Bio:", bioTextArea = new TextArea() )                
        );
        VBox addressPane = new VBox( 10 );
        addressPane.getChildren().addAll(    
                row( "Street:", streetTextField = new TextField()),
                row( "City:", cityTextField = new TextField()),
                row( "State:", statetTextField = new TextField()),
                row( "Zip:", zipTextField = new TextField()),
                row( "", buttons())                
        );
        grid.add(bookPane, 0, 1,2,2);
        grid.add(authorPpane, 0, 3,2,2);
        grid.add(addressPane, 0, 5,2,2);

        //***************************************

        Button addBookBtn = new Button("Add Book");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(addBookBtn);
        grid.add(hbBtn, 1, 8);
        
        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_RIGHT);
        messageBox.getChildren().add(messageBar);;
        grid.add(messageBox, 1, 9);

        addBookBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		try {  	
        				ControllerInterface c = new SystemController();
        				if(isbnTextField.getText().trim().isEmpty()||
        				   titleTextField.getText().trim().isEmpty()) {
        	    			
        					throw new LibrarySystemException("All book fields must be provided.");	
        				}        
        				
        				if(c.searchBookByIsbn(isbnTextField.getText().trim())!=null)
        					throw new LibrarySystemException("The book is already in the system!");

        					
        				if(!isbnTextField.getText().trim().matches("[0-9-]*"))
    				
    					    throw new LibrarySystemException("The ISBN format is not correct number.");	
    			
        				authorRead();
	        			        		
        			Book book = new  Book(isbnTextField.getText().trim(), 
        								  titleTextField.getText().trim(), 
        							      maxCheckoutComboBox.getValue(),
        							      authors);
        			c.addBook(book);
        			messageBar.setFill(NewStart.Colors.green);
             	    messageBar.setText("Adding Book successful");
        		} catch(LibrarySystemException ex) {
        			messageBar.setFill(NewStart.Colors.red);
        			messageBar.setText(ex.getMessage());
        		}        	   
        	}
        });

        NewStart.logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override 
        	public void handle(ActionEvent e) {
        		NewStart.resetWindow();
          	}
        });        
    }    
    
    public Node row( String labelText, Node field ) {
        HBox row = new HBox( 10 );
        Label label = new Label( labelText );
        label.setMinWidth( 120 );
        row.getChildren().addAll( label, field );
        return row;
    }

    Button additionalAutorBtn = new Button("Additional Author" );
    public Node buttons() {
        HBox buttons = new HBox( 10 );
        
    additionalAutorBtn.setOnAction(new EventHandler<ActionEvent>() {
        		
	        	public void handle(ActionEvent e) {	        		
	        		try {	        			
							authorRead();
							clearTextFields();
					} catch (LibrarySystemException ex) {
	        			messageBar.setFill(NewStart.Colors.red);
	        			messageBar.setText(ex.getMessage());					}
	        	}
   }
        		                );             		

        buttons.getChildren().addAll( additionalAutorBtn);
      
        return buttons;
    }
    
    @SuppressWarnings("unused")
	private final void authorRead() throws LibrarySystemException {		

		if(
			streetTextField.getText().trim().isEmpty()|| cityTextField.getText().trim().isEmpty() ||
			statetTextField.getText().trim().isEmpty() || zipTextField.getText().trim().isEmpty()  ||
			fNameField.getText().trim().isEmpty() || lNameField.getText().trim().isEmpty() ||
			bioTextArea.getText().trim().isEmpty() ||telephoneField.getText().trim().isEmpty()) {
			
			throw new LibrarySystemException("All author fields must be provided.");					
		}	       		
		try {
			Integer.parseInt(telephoneField.getText().trim());
		} catch (NumberFormatException e) {
			throw new LibrarySystemException("The telephone digits must be integers.");
		}
		try {
			Integer.parseInt(zipTextField.getText().trim());
		} catch (NumberFormatException e) {
			throw new LibrarySystemException("Zip format is incorrect.");
		}
	Address address = new Address(  streetTextField.getText().trim(), 
			cityTextField.getText().trim(), 
			statetTextField.getText().trim(), 
			zipTextField.getText().trim());      								   
   Author author = new Author(		fNameField.getText().trim(),
	   		lNameField.getText().trim(),
	   		telephoneField.getText().trim(),
	   		address, bioTextArea.getText().trim());
	
}
}
