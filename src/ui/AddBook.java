package ui;

import java.util.ArrayList;
import java.util.List;

import business.Address;
import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	 
	 //*************author and address fields
	 
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
    
    @SuppressWarnings("unchecked")
	public void init() { 
        grid = new GridPane();
        grid.setId("addbook-top-container");
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
        TextField maxCheckoutTextField;    
               
        VBox bookPane = new VBox( 10 );
        bookPane.getChildren().addAll(
                row( "ISBN:", isbnTextField = new TextField() ),
                row( "Title:", titleTextField = new TextField() ),
                row( "Max Checkout Length:", maxCheckoutTextField = new TextField() )
                
        );
        
        VBox authorPpane = new VBox( 10 );
        authorPpane.getChildren().addAll(       

                row( "First Name:", fNameField = new TextField() ),
                row( "Last Name:", lNameField = new TextField() ),
                row( "Telephone No:", telephoneField = new TextField()),
                row( "Bio:", bioTextArea = new TextArea() )
                
        );
        VBox addressPpane = new VBox( 10 );
        addressPpane.getChildren().addAll(    
                row( "Street:", streetTextField = new TextField()),
                row( "City:", cityTextField = new TextField()),
                row( "State:", statetTextField = new TextField()),
                row( "Zip:", zipTextField = new TextField()),
                row( "", buttons())                
        );
        grid.add(bookPane, 0, 1,2,2);
        grid.add(authorPpane, 0, 3,2,2);
        grid.add(addressPpane, 0, 5,2,2);

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
        			Address address = new Address(streetTextField.getText().trim(), 
							cityTextField.getText().trim(), 
							statetTextField.getText().trim(), 
							zipTextField.getText().trim());      								   
	        		Author author = new Author(fNameField.getText().trim(),
					  lNameField.getText().trim(),
					  telephoneField.getText().trim(),
					  address, bioTextArea.getText().trim());
        			Book book = new  Book(isbnTextField.getText().trim(), titleTextField.getText().trim(), 
        							      Integer.parseInt(maxCheckoutTextField.getText().trim()), authors);
        			ControllerInterface c = new SystemController();
        			c.addBook(book);
        			messageBar.setFill(Start.Colors.green);
             	    messageBar.setText("Adding Book successful");
        		} catch(Exception ex) {
        			messageBar.setFill(Start.Colors.red);
        			messageBar.setText("Error! " + ex.getMessage());
        		}        	   
        	}
        });

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override 
        	public void handle(ActionEvent e) {
        		NewStart.resetWindow();
          	}
        });
        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.BOTTOM_LEFT);
        hBack.getChildren().add(logoutBtn);
        grid.add(hBack, 0, 9);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);        
    }	
    
    
    public Node row( String labelText, Node field ) {
        HBox row = new HBox( 10 );
        Label label = new Label( labelText );
        label.setMinWidth( 120 );
        row.getChildren().addAll( label, field );
        return row;
    }

    public Node buttons() {
        HBox buttons = new HBox( 10 );
        Button addAnotherAutor = new Button("Add More Author" );
        addAnotherAutor.setOnAction(new EventHandler<ActionEvent>() {
        		
	        	public void handle(ActionEvent e) {	        		
	        	
	        		messageBar.setText( "Please add the next author details."); 
	        	
	        		Address address = new Address(streetTextField.getText().trim(), 
	        										cityTextField.getText().trim(), 
	        										statetTextField.getText().trim(), 
	        										zipTextField.getText().trim());      								   
					Author author = new Author(fNameField.getText().trim(),
											  lNameField.getText().trim(),
											  telephoneField.getText().trim(),
											  address, bioTextArea.getText().trim());
					authors.add(author);
					clearTextFields();
					System.out.println("The authors of this book are: "+author);
	        	}}
        		);    							
        		
        buttons.getChildren().addAll( addAnotherAutor);
      
        return buttons;
    }
}
