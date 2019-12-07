package ui;

import business.ControllerInterface;
import business.LibraryMember;
import business.LibrarySystemException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SearchMember extends Stage implements LibWindow {
	public static final SearchMember INSTANCE = new SearchMember();
	
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
    private SearchMember () {}
    
    public void init() { 
        grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Search Member");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);
        //***************************/
        
        TextField memberIdField;
        TextField fNameField;
        TextField lNameField;
        TextField telephoneField;
        
        TextField streetTextField;
        TextField cityTextField ;
        TextField statetTextField;
        TextField zipTextField ;
        
        VBox memberPpane = new VBox( 10 );
        memberPpane.getChildren().addAll(
        		
                row( "Member ID:", memberIdField = new TextField() ),
                row( "First Name:", fNameField = new TextField() ),
                row( "Last Name:", lNameField = new TextField() ),
                row( "Telephone No:", telephoneField = new TextField())
        );
        VBox addressPpane = new VBox( 10 );
        addressPpane.getChildren().addAll(    
                row( "Street:", streetTextField = new TextField()),
                row( "City:", cityTextField = new TextField()),
                row( "State:", statetTextField = new TextField()),
                row( "Zip:", zipTextField = new TextField())
        );
       
        grid.add(memberPpane, 0, 1);
        grid.add(addressPpane, 0, 2);  
               
        Button searchBtn = new Button("Search");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(searchBtn);
        grid.add(hbBtn, 1, 3);

        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_RIGHT);
        messageBox.getChildren().add(messageBar);;
        grid.add(messageBox, 0, 4);
        
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		try {
        			ControllerInterface c = new SystemController();
        			LibraryMember member = c.searchMemberById(memberIdField.getText().trim());
        			
        			//Populate the fields
        	         fNameField.setText(member.getFirstName());
        	         lNameField.setText(member.getLastName());
        	         telephoneField.setText(member.getTelephone());
        	        
        	         streetTextField.setText(member.getAddress().getStreet());
        	         cityTextField.setText(member.getAddress().getCity());
        	         statetTextField.setText(member.getAddress().getState());
        	         zipTextField.setText(member.getAddress().getZip());
        		
        			messageBar.setFill(Start.Colors.green);
             	    messageBar.setText("Member successfully found");
        		} catch(NullPointerException | LibrarySystemException ex) {
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
    // label and textfield combiner
    public Node row( String labelText, Node field ) {
        HBox row = new HBox( 10 );
        Label label = new Label( labelText );
        label.setMinWidth( 120 );
        row.getChildren().addAll( label, field );
        return row;
    }
	public Text getMessageBar() {
		return messageBar;
	}
    
}

