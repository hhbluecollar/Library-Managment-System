package ui;

import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
		        splashVBox.setMinSize(500, 20);
		        splashVBox.setId("spalsh-box");
			   // splashVBox.setBackground(Background.EMPTY);
		        splashVBox.autosize();
		        Label splashLabelSub = new Label("Please select a task");
		        Label splashLabel = new Label("Welcome To Yegna Library System");
		        
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


