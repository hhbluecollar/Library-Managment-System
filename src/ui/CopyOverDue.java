package ui;


import business.BookOverDue;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CopyOverDue extends Stage implements LibWindow {
	public static final CopyOverDue INSTANCE = new CopyOverDue();
 	private static GridPane grid;
    public static  TableView<BookOverDue> booksTableView = new TableView<BookOverDue>();
    
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
	
	/* This class is a singleton */
	private CopyOverDue() {}
	
	public void init() {
		grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(1, 1, 1, 1));
        Button checkBtn = new Button("Check");
        Label isbnLable = new Label("ISBN:");
		TextField isbnTextField = new TextField();	

        Text scenetitle = new Text("Book Copy Overdue Check");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);

		ControllerInterface ci = new SystemController();		

		 checkBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	          public void handle(ActionEvent e) {
	       		 try {
	     			ci.overdueCheck(isbnTextField.getText().trim());	     			
	     		} catch (LibrarySystemException e1) {
	     			e1.getMessage();
	     		}
	         }
	        });			 
	        VBox tableViewVBox = new VBox();
	        tableViewVBox.setPadding(new Insets(25, 25, 25, 25));;
	       tableViewVBox.getChildren().add(booksTableView);
	        
		grid.add(tableViewVBox, 0, 2,5,1);		
		grid.add(isbnLable, 0, 1);
		grid.add(isbnTextField, 1, 1);
		//grid.add(booksTableView, 0, 4);
		grid.add(checkBtn, 2, 1,4,1);
		NewStart.logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override 
        	public void handle(ActionEvent e) {
        		NewStart.resetWindow();
          	}
        });
		
        isInitialized(true);
	}
}
