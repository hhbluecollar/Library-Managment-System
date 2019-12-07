package ui;

import java.util.Collections;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckoutEntry;
import business.ControllerInterface;
import business.LibraryMember;
import business.LoginException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

public class CheckoutStatus extends Stage implements LibWindow {
	public static final CheckoutStatus INSTANCE = new CheckoutStatus();
	
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
    private CheckoutStatus () {}
    
    public void init() { 
        grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Checkout Record Status:");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //********************************//
        
        TextField memberIdField;   
        TextArea statusArea;
       // TextField authorTextField ;
      //  TextField dueDateTextField;
       // TextField checkOutTextField ;
        
        VBox statusPane = new VBox( 10 );
        statusPane.getChildren().addAll(
        		
                row( "Member ID:", memberIdField = new TextField() ),
                row( "Checkout Detail:", statusArea = new TextArea() )
        );    
       
        grid.add(statusPane, 0, 1);
               
        Button searchBtn = new Button("Check Records");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(searchBtn);
        grid.add(hbBtn, 0, 6);

        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_RIGHT);
        messageBox.getChildren().add(messageBar);;
        grid.add(messageBox, 0, 7);
        
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		try {        			
        			//Populate the fields
        					ControllerInterface ci = new SystemController();

        					LibraryMember member = ci.searchMemberById(memberIdField.getText());
        					List<CheckoutEntry> checkOuts = member.getCheckoutRecord().getCheckoutEntry();
               	           System.out.println(checkOuts.size());

        					StringBuilder sb = new StringBuilder();
        					int bkNo =0;
        					sb.append("The checkout records for "+member.getLastName()+" "+member.getFirstName()+" are: \n");
       					System.out.println(checkOuts.size());
        					for(CheckoutEntry s: checkOuts) {
        						bkNo++;
        						sb.append(bkNo+". "+s.getBookCopy().getBook().getTitle()+"\n");
        						sb.append("\t\t"+"Checkout Date: "+s.getCheckoutdate()+"\n");
        						sb.append("\t\t" +"Due date: "+s.getDueDate()+ "\n");
        						sb.append("\t\t" +"Max checkout length: "+s.getBookCopy().getBook().getMaxCheckoutLength()+ "\n");
        					}
        					statusArea.setText(sb.toString());
        	    			NewStart.topContainer.setCenter(getGrid()
        	    		);        			
        		
        			messageBar.setFill(Start.Colors.green);
             	    messageBar.setText("Record successfully found");
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
        
    public Node row( String labelText, Node field ) {
        HBox row = new HBox( 10 );
        Label label = new Label( labelText );
        label.setMinWidth( 120 );
        row.getChildren().addAll( label, field );
        return row;
    }
}

