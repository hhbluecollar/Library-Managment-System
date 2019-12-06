package ui;

import java.util.Collections;

import business.SystemController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		        grid.setPadding(new Insets(25, 25, 25, 0));
		        //grid.setMaxSize(300, 600);
				VBox imageHolder = new VBox();
				Image image = new Image("ui/library2.jpg", 500, 600, false, true);

		        // simply displays in ImageView the image as is
		        ImageView iv = new ImageView();
		        iv.setImage(image);
		        imageHolder.getChildren().add(iv);
		        imageHolder.setAlignment(Pos.CENTER);
		        VBox splashBox = new VBox();
		       splashBox.setMaxSize(500, 590);

		        splashBox.setId("spalsh-box");
		         Label splashLabelSub = new Label("Please select a task");

		        Label splashLabel = new Label("Welcome Yegna Library System");
		        if(SystemController.currentAuth==null)
		        splashLabelSub.setText("Please Login");
		        splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 30));
		        splashLabelSub.setFont(Font.font("Trajan Pro", FontWeight.BLACK, 20));
		        
		        splashBox.getChildren().add(splashLabel);
		        splashBox.getChildren().add(splashLabelSub);

		        splashBox.setAlignment(Pos.TOP_CENTER);
		        grid.add(splashBox,0,1);
		        grid.add(imageHolder,0,2);
				
		        Scene scene = new Scene(grid);
		        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		        setScene(scene);				
			}	
}


