
package ui;


import business.ControllerInterface;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
        
        TextField memberIdField;   
        TextArea statusArea;
        
        VBox statusPane = new VBox( 10 );
        statusPane.getChildren().addAll(
        		
                row( "Member ID:", memberIdField = new TextField() ),
                row( "Checkout Detail:", statusArea = new TextArea() )
        );    
       
        grid.add(statusPane, 0, 1);
               
        Button searchBtn = new Button("Check Record");
        HBox hbBtn = new HBox(10);
        searchBtn.setDefaultButton(true);
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
        					String string = ci.checkoutStatus(memberIdField.getText());
        					statusArea.setText(string);
        	    			NewStart.topContainer.setCenter(getGrid()
        	    		);      			
        		
        			messageBar.setFill(NewStart.Colors.green);
             	    messageBar.setText("Record successfully found");
        		} catch(Exception ex) {
        			messageBar.setFill(NewStart.Colors.red);
        			messageBar.setText( ex.getMessage());
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

