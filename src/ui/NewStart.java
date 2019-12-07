package ui;

import java.util.Collections;
import java.util.List;


import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class NewStart extends Application{	
	
        public  static Hyperlink login = new Hyperlink("Login");
        public  static Hyperlink addMember =  new Hyperlink("Add Memeber");
        public  static Hyperlink searchEditMem =  new Hyperlink("Search\\Edit Memeber");
        public  static Hyperlink checkoutBook = new Hyperlink("Checkout Book");
        public  static Hyperlink addBook = new Hyperlink("Add Book");
        public  static Hyperlink checkoutStatus = new Hyperlink("Check Book Status");
        public  static Hyperlink allMemeberId = new Hyperlink("All Members ID");
        public  static Hyperlink allBookId = new Hyperlink("All Books ID");
        public  static Hyperlink addBookCopy = new Hyperlink("Add Book Copy");
        public  static BorderPane topContainer;
        public  static GridPane leftContainer;
        public  static GridPane rightContainer;        
        public static Button  logoutBtn = new Button("Logout");        

	
	public static void main(String[] args) {
			launch(args);
		}
		private static Stage primStage = null;
		public static Stage primStage() {
			return primStage;
		}
		
		public static class Colors {
			static Color green = Color.web("#034220");
			static Color red = Color.FIREBRICK;
		}

/*		private static Stage[] allWindows = { 
			LoginWindow.INSTANCE,
			AllMembersWindow.INSTANCE,	
			AllBooksWindow.INSTANCE,
		};	
*/						
		//************************************
		public static void resetWindow() {
    		SystemController.currentAuth=null;
    		LoginWindow.INSTANCE.init();
			topContainer.setCenter(LoginWindow.getGrid());
			LoginWindow.INSTANCE.clear();

			checkoutBook.setDisable(true);
			addBook.setDisable(true);
			addBookCopy.setDisable(true);
			allBookId.setDisable(true);
			addMember.setDisable(true);
			searchEditMem.setDisable(true);
			allMemeberId.setDisable(true);
			checkoutStatus.setDisable(true);
     	    login.setDisable(false);
     	    logoutBtn.setVisible(false);

//			if(!WelcomeWindow.INSTANCE.isInitialized()) {
//				WelcomeWindow.INSTANCE.init();
//
//			}
			//rightContainer.add(WelcomeWindow.getGrid(), 0, 0);
		}
		
		//************************************
		@Override
		public void start(Stage primaryStage) {
			primStage = primaryStage;
			primaryStage.setTitle("The Library System");
					
			topContainer= new BorderPane();	
			topContainer.setId("top-container");
	        leftContainer = new GridPane();	        
			leftContainer.setId("left-container");
			leftContainer.setPrefSize(300, 600);
			leftContainer.setHgap(10);
			leftContainer.setVgap(10);
			leftContainer.setPadding(new Insets(10,10,10,10));
			
			//Populate left container
			leftContainer.add(login, 1, 4); 
			leftContainer.add(addMember, 1, 5); 	 addMember.setDisable(true);
			leftContainer.add(searchEditMem, 1, 6);	 searchEditMem.setDisable(true);
			leftContainer.add(checkoutBook, 1, 7);	 checkoutBook.setDisable(true);
			leftContainer.add(addBook, 1, 8);		 addBook.setDisable(true);
			leftContainer.add(checkoutStatus, 1, 9); checkoutStatus.setDisable(true);
			leftContainer.add(allMemeberId, 1, 10);	 allMemeberId.setDisable(true);
			leftContainer.add(allBookId, 1, 11);	 allBookId.setDisable(true);
			leftContainer.add(addBookCopy, 1, 12);	 addBookCopy.setDisable(true);			
			leftContainer.add(logoutBtn, 1, 17);     logoutBtn.setVisible(false);
			logoutBtn.setMinSize(10, 2); 			
			
			rightContainer = new GridPane();
			rightContainer.setId("right-container");
			
		    final EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>(){
		    	
				@Override
				public void handle(ActionEvent event) {
		            Hyperlink clickedLink = (Hyperlink) event.getSource();

					if(clickedLink.equals(login)) {
						if(!LoginWindow.INSTANCE.isInitialized()) {
		    				LoginWindow.INSTANCE.init();
		    			}
		    			LoginWindow.INSTANCE.clear();
		    			topContainer.setCenter(LoginWindow.getGrid());

					}
					if(clickedLink.equals(addMember)) {
						if(!AddMember.INSTANCE.isInitialized()) {
							AddMember.INSTANCE.init();
		    			}
						AddMember.INSTANCE.clear();
		    			topContainer.setCenter(AddMember.getGrid());
					}
					if(clickedLink.equals(addBookCopy)) {
						if(!AddBookCopy.INSTANCE.isInitialized()) {
							AddBookCopy.INSTANCE.init();
		    			}
						AddBookCopy.INSTANCE.clear();
		    			topContainer.setCenter(AddBookCopy.getGrid());
					}
					if(clickedLink.equals(addBook)) {
						if(!AddBook.INSTANCE.isInitialized()) {
							AddBook.INSTANCE.init();
		    			}
						AddBook.INSTANCE.clear();
		    			topContainer.setCenter(AddBook.getGrid());
					}
					if(clickedLink.equals(searchEditMem)) {
						if(!SearchMember.INSTANCE.isInitialized()) {
							SearchMember.INSTANCE.init();
						}
						SearchMember.INSTANCE.getMessageBar().setFill(Start.Colors.green);
						SearchMember.INSTANCE.getMessageBar().setText("Insert member id to search.");			    								
		    			topContainer.setCenter(SearchMember.getGrid());
					}
					if(clickedLink.equals(checkoutBook)) {
						if(!CheckoutBook.INSTANCE.isInitialized()) {
							CheckoutBook.INSTANCE.init();
		    			}
						CheckoutBook.INSTANCE.clear();
		    			topContainer.setCenter(CheckoutBook.getGrid());
					}
					if(clickedLink.equals(checkoutStatus)) {
						if(!CheckoutStatus.INSTANCE.isInitialized()) {
							CheckoutStatus.INSTANCE.init();
		    			}
						CheckoutStatus.INSTANCE.clear();
		    			topContainer.setCenter(CheckoutStatus.getGrid());
					}			
				}		    
		    };
		    
		   // login.setOnAction(myHandler);
		    addMember.setOnAction(myHandler);
		    searchEditMem.setOnAction(myHandler);
		    checkoutBook.setOnAction(myHandler);
		    addBook.setOnAction(myHandler);
		    checkoutStatus.setOnAction(myHandler);
		    addBookCopy.setOnAction(myHandler);

			login.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	    			if(!LoginWindow.INSTANCE.isInitialized()) {
	    				LoginWindow.INSTANCE.init();
	    			}
	    			LoginWindow.INSTANCE.clear();
	    			topContainer.setCenter(LoginWindow.getGrid());				

	            }
	        });			
								
			allBookId.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
					if(!AllBooksWindow.INSTANCE.isInitialized()) {
						AllBooksWindow.INSTANCE.init();
					}
					ControllerInterface ci = new SystemController();
					List<String> ids = ci.allBookIds();
					Collections.sort(ids);
					System.out.println(ids);

					StringBuilder sb = new StringBuilder();
					for(String s: ids) {
						sb.append(s + "\n");
					}
					AllBooksWindow.INSTANCE.setData(sb.toString());
	    			topContainer.setCenter(AllBooksWindow.getGrid());				
	            }
			});
			
			allMemeberId.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
					if(!AllMembersWindow.INSTANCE.isInitialized()) {
						AllMembersWindow.INSTANCE.init();
					}
					ControllerInterface ci = new SystemController();
					List<String> ids = ci.allMemberIds();
					Collections.sort(ids);
					System.out.println(ids);
					StringBuilder sb = new StringBuilder();
					for(String s: ids) {
						sb.append(s + "\n");
					}
					System.out.println(sb.toString());
					AllMembersWindow.INSTANCE.setData(sb.toString());
	    			topContainer.setCenter(AllMembersWindow.getGrid());				
	            }
			});	
		
			
			if(!WelcomeWindow.INSTANCE.isInitialized()) {
				WelcomeWindow.INSTANCE.init();
			}
			WelcomeWindow.INSTANCE.clear();
			rightContainer.add(WelcomeWindow.getGrid(), 0, 0);
			topContainer.setLeft(leftContainer);
			topContainer.setCenter(rightContainer);				

			Scene scene = new Scene(topContainer,800,600);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
			primaryStage.show();
		}		
}

