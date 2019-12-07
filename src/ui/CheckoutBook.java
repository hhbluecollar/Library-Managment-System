package ui;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckoutBook extends Stage implements LibWindow {


	public static final CheckoutBook INSTANCE = new CheckoutBook();
	
	private boolean isInitialized = false;
	
    private static GridPane grid;	
	public static GridPane getGrid() {
		return grid;
	}
	
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
    private CheckoutBook () {}
    
    public void init() { 
        grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Checkout Book");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);

        Label lblMemID = new Label("Member ID:");
        Label lblIsbn = new Label("ISBN:");
//        Label lblMaxLength = new Label("Max Checkout Length:");
//        Label lblAuthor = new Label("Author:");
       
        TextField memIDTextField = new TextField();
        TextField isbnTextField = new TextField();

        grid.add(lblMemID, 0, 1);
        grid.add(lblIsbn, 0, 2);
       
        grid.add(memIDTextField, 1, 1);
        grid.add(isbnTextField, 1, 2);
        

        Button checkOutBtn = new Button("Checkout");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(checkOutBtn);
        grid.add(hbBtn, 1, 3);

        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_RIGHT);
        messageBox.getChildren().add(messageBar);;
        grid.add(messageBox, 1, 4);
        
        checkOutBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		try {
        			ControllerInterface c = new SystemController();
        			if(c.checkoutBook(memIDTextField.getText().trim(), 
        					isbnTextField.getText().trim())) {
        			messageBar.setFill(Start.Colors.green);
             	    messageBar.setText("Checkout successful");
        			}
        			else
        				System.out.println("Either the mem or the ");
        			} catch(Exception ex) {
        			messageBar.setFill(Start.Colors.red);
        			messageBar.setText("Error! " + ex.getMessage());
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
