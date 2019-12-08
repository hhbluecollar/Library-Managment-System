package ui;

import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class WelcomeWindow extends Stage implements LibWindow{
	public static final WelcomeWindow INSTANCE = new WelcomeWindow();
	
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
    private WelcomeWindow () {}				
		
			@Override
			public void init() {
				grid = new GridPane();
		        grid.setId("welcome-container");
		        grid.setAlignment(Pos.CENTER);
		        grid.setHgap(10);
		        grid.setVgap(10);
				
		        VBox splashVBox = new VBox();
		        splashVBox.setMinSize(500, 30);
		        splashVBox.setId("spalsh-box");
			   // splashVBox.setBackground(Background.EMPTY);
		        splashVBox.autosize();
		        Text splashLabelSub = new Text("Please select a task");
		        Text splashLabel = new Text("Welcome To Legeta Library System");
		        
		        //DropShadow effect 
		        DropShadow dropShadow = new DropShadow();
		        dropShadow.setOffsetX(5);
		        dropShadow.setOffsetY(5);
		         
		        //Adding text and DropShadow effect to it
		        splashLabelSub.setTextAlignment(TextAlignment.CENTER);
		        splashLabelSub.setEffect(dropShadow);		        
		        splashLabel.setTextAlignment(TextAlignment.CENTER);
		        splashLabel.setEffect(dropShadow);
		        
		        
		        if(SystemController.currentAuth==null)
		        	splashLabelSub.setText("Please Login");
		        splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 30));
		        splashLabelSub.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 20));
			    splashVBox.setAlignment(Pos.BOTTOM_CENTER);

		        splashVBox.getChildren().add(splashLabel);
		        splashVBox.getChildren().add(splashLabelSub);
			    grid.getChildren().add(splashVBox);
			    
		        NewStart.logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
		        	@Override 
		        	public void handle(ActionEvent e) {
		        		NewStart.resetWindow();
		          	}
		        });		
			}	
}


