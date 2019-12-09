package ui;

import business.SystemController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class NewStart extends Application{	
	
        public  static Hyperlink login = new Hyperlink("Login");
        public  static Hyperlink addMember =  new Hyperlink("Add Memeber");
        public  static Hyperlink searchEditMem =  new Hyperlink("Search\\Edit Memeber");
        public  static Hyperlink checkoutBook = new Hyperlink("Checkout Book");
        public  static Hyperlink addBook = new Hyperlink("Add Book");
        public  static Hyperlink checkoutStatus = new Hyperlink("Checkout Record Status");
        public  static Hyperlink allMemeberId = new Hyperlink("All Members Info");
        public  static Hyperlink allBookId = new Hyperlink("All Books Info");
        public  static Hyperlink addBookCopy = new Hyperlink("Add Book Copy");
        public  static BorderPane topContainer;
        public  static GridPane leftContainer;
        public  static GridPane rightContainer;        
        public  static Button  logoutBtn = new Button("Logout");        

	
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
		private void handleWindowClosing() {
			
			primStage.setOnCloseRequest(event -> System.exit(0));            
	      				
		}			
		public static void resetWindow() {
    		SystemController.currentAuth=null;
    		WelcomeWindow.INSTANCE.init();
			topContainer.setCenter(WelcomeWindow.getGrid());
			WelcomeWindow.INSTANCE.clear();

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
		}
		
		@Override
		public void start(Stage primaryStage) {
			primStage = primaryStage;
			primaryStage.setTitle("Legeta Library System");
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
						SearchMember.INSTANCE.getMessageBar().setFill(NewStart.Colors.green);
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
					if(clickedLink.equals(allBookId)) {
						if(!AllBooksWindow.INSTANCE.isInitialized()) {
							AllBooksWindow.INSTANCE.init();
		    			}
		    			topContainer.setCenter(AllBooksWindow.getGrid());
					}	
					if(clickedLink.equals(allMemeberId)) {
						if(!AllMembersWindow.INSTANCE.isInitialized()) {
							AllMembersWindow.INSTANCE.init();
		    			}
		    			topContainer.setCenter(AllMembersWindow.getGrid());
					}
				}		    
		    };
		    
		    addMember.setOnAction(myHandler);
		    searchEditMem.setOnAction(myHandler);
		    checkoutBook.setOnAction(myHandler);
		    addBook.setOnAction(myHandler);
		    checkoutStatus.setOnAction(myHandler);
		    addBookCopy.setOnAction(myHandler);
		    allBookId.setOnAction(myHandler);
		    allMemeberId.setOnAction(myHandler);
		    
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
			
			if(!WelcomeWindow.INSTANCE.isInitialized()) {
				WelcomeWindow.INSTANCE.init();
			}
			WelcomeWindow.INSTANCE.clear();
			rightContainer.add(WelcomeWindow.getGrid(), 0, 0);
			topContainer.setLeft(leftContainer);
			topContainer.setCenter(rightContainer);				
			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));

			Scene scene = new Scene(topContainer,800,600);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
			handleWindowClosing();
			primaryStage.show();
		}		
}

