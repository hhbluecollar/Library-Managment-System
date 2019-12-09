package ui;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.LibrarySystemException;
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

public class AddMember extends Stage implements LibWindow{

     	private static GridPane grid;
     	public static GridPane getGrid() {
     		return grid;
     	}
		public static final AddMember INSTANCE = new AddMember();
		
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
	    private AddMember () {}
	    
	    public void init() { 
	    	grid = new GridPane();
	        grid.setId("top-container");
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));

	        Text scenetitle = new Text("Add New Member");
	        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
	        grid.add(scenetitle, 0, 0, 1, 1);

	        Label memberIdLbl = new Label("Member ID:");
	        Label fNameLbl = new Label("First Name:");
	        Label lNameLbl = new Label("Last Name:");
	        Label telNoLbl = new Label("Telephone Number:");

	        Label streetLbl = new Label("Street:");
	        Label cityLbl = new Label("City:");
	        Label stateLbl = new Label("State:");
	        Label zipLbl = new Label("Zip Code:");

	        
	        TextField membreTextField = new TextField();
	        TextField fNameTextField = new TextField();
	        TextField lNameTextField = new TextField();
	        TextField telNoTextField = new TextField();
	        
	        TextField streetTextField = new TextField();
	        TextField cityTextField = new TextField();
	        TextField statetTextField = new TextField();
	        TextField zipTextField = new TextField();

	        grid.add(memberIdLbl, 0, 1);
	        grid.add(fNameLbl, 0, 2);
	        grid.add(lNameLbl, 0, 3);
	        grid.add(telNoLbl, 0, 4);

	        grid.add(streetLbl, 0, 5);
	        grid.add(cityLbl, 0, 6);
	        grid.add(stateLbl, 0, 7);
	        grid.add(zipLbl, 0, 8);

	        grid.add(membreTextField, 1, 1);
	        grid.add(fNameTextField, 1, 2);
	        grid.add(lNameTextField, 1, 3);
	        grid.add(telNoTextField, 1, 4);
	        
	        grid.add(streetTextField, 1, 5);
	        grid.add(cityTextField, 1, 6);
	        grid.add(statetTextField, 1, 7);
	        grid.add(zipTextField, 1, 8);               

	        Button addMemberBtn = new Button("Add Member");
	        HBox hbBtn = new HBox(10);
	        addMemberBtn.setDefaultButton(true);

	        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	        hbBtn.getChildren().add(addMemberBtn);
	        grid.add(hbBtn, 1, 10);

	        HBox messageBox = new HBox(10);
	        messageBox.setAlignment(Pos.BOTTOM_RIGHT);
	        messageBox.getChildren().add(messageBar);;
	        grid.add(messageBox, 1, 12,2,2);
	        
	        addMemberBtn.setOnAction(new EventHandler<ActionEvent>() {
	        	@Override
	        	public void handle(ActionEvent e) {
        			
	        		ControllerInterface c = new SystemController();
	        		
	        
	        		try {     			
	        			if(c.searchMemberByIdIsAvaialable(membreTextField.getText().trim()))
		        			throw new LibrarySystemException("This member is already in the system!");
	        			Address address = new Address(streetTextField.getText().trim(), 
	        										  cityTextField.getText().trim(), 
	        										  statetTextField.getText().trim(), 
	        										  zipTextField.getText().trim()
	        										  );
	        			LibraryMember member = new LibraryMember(
	        									membreTextField.getText().trim(),
	        									fNameTextField.getText().trim(),
	        									lNameTextField.getText().trim(),
	        									telNoTextField.getText().trim(),
	        									address
	        									);
	        			c.addMember(member,true);
	        			messageBar.setFill(NewStart.Colors.green);
	             	    messageBar.setText("Member successfuly Added");
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


