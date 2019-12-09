package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AllBooksWindow extends Stage implements LibWindow {
	public static final AllBooksWindow INSTANCE = new AllBooksWindow();
 	private static GridPane grid;
    private TableView<Book> booksTableView;
    
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void populateTable(List<Book> bookList) {
		 TableColumn<Book,String> bookTitleCol = new TableColumn<>("Title");
		 TableColumn<Book,String> isbnCol = new TableColumn<>("ISBN");
		 TableColumn<Book,String> maxChkLengCol = new TableColumn<>("Max Length");
		 TableColumn<Book,Integer> noOfCopiesCol = new TableColumn<>("Copy No.");
		 TableColumn<Book,Integer > noOfAuthorsCol = new TableColumn<>("Author No.");

		 bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		 isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));		
		 maxChkLengCol.setCellValueFactory(new PropertyValueFactory<>("maxCheckoutLength"));		
		 noOfCopiesCol.setCellValueFactory(new PropertyValueFactory<>("copiesNo"));		
		 noOfAuthorsCol.setCellValueFactory(new PropertyValueFactory<>("authorsNo"));	
		 isbnCol.setSortType(TableColumn.SortType.DESCENDING);
		 
		 booksTableView.getColumns().setAll(bookTitleCol, isbnCol, maxChkLengCol, noOfCopiesCol,noOfAuthorsCol);
	     ObservableList data = FXCollections.observableList(bookList);
		 booksTableView.setItems(data);
	}
	
	/* This class is a singleton */
	private AllBooksWindow() {}
	
	public void init() {
		grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(1, 1, 1, 1));

        Text scenetitle = new Text("All Book Info");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);
				
		ControllerInterface ci = new SystemController();
		HashMap<String, Book> booksHashMap = ci.allBookIds();

		 booksTableView = new TableView<>();		
		 List<Book> bookList = 	new ArrayList<>();
		 bookList.addAll(booksHashMap.values());
		 populateTable(bookList);
		 
	        VBox tableViewVBox = new VBox();
	        tableViewVBox.setPadding(new Insets(25, 25, 25, 25));;
	        tableViewVBox.getChildren().add(booksTableView);
		grid.add(tableViewVBox, 0, 1,5,1);
		
		NewStart.logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override 
        	public void handle(ActionEvent e) {
        		NewStart.resetWindow();
          	}
        });
		
        isInitialized(true);
	}
}
