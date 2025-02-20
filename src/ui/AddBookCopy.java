package ui;

import business.ControllerInterface;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddBookCopy extends Stage implements LibWindow {
	public static final AddBookCopy INSTANCE = new AddBookCopy();
	
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
    private AddBookCopy () {}
    
    public void init() { 
    	
        grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add Copy");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);

        Label lblISBN = new Label("ISBN:");
        Label lblCopy = new Label("Number of Copy:");     
        TextField isbnTextField = new TextField();
        TextField copyTextField = new TextField();
       
        grid.add(lblISBN, 0, 1);
        grid.add(lblCopy, 0, 2);       
        grid.add(isbnTextField, 1, 1);
        grid.add(copyTextField, 1, 2);   
        
        Button addCopyBtn = new Button("Add Copy");
        HBox hbBtn = new HBox(10);
        addCopyBtn.setDefaultButton(true);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(addCopyBtn);
        grid.add(hbBtn, 1, 3);

        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_RIGHT);
        messageBox.getChildren().add(messageBar);;
        grid.add(messageBox, 0, 4,3,1);
        
        addCopyBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		try {
        			ControllerInterface c = new SystemController();
        			String copyNum = copyTextField.getText();	
        			String isbn = isbnTextField.getText().trim();
        			c.addBookCopy(isbn, copyNum); 
        			
        			messageBar.setFill(NewStart.Colors.green);
             	    messageBar.setText("Adding copy successfully");
        		} catch(Exception ex) {
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
}
