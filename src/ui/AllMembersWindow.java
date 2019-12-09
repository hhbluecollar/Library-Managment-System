package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Book;
import business.ControllerInterface;
import business.LibraryMember;
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

public class AllMembersWindow extends Stage implements LibWindow {
	public static final AllMembersWindow INSTANCE = new AllMembersWindow();
 	private static GridPane grid;
 	@SuppressWarnings("rawtypes")
	private TableView memberTableView;
 	
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
	public void populateTable(List<LibraryMember> memList) {
		 TableColumn<Book,String> memberIdCol = new TableColumn<>("Member ID");
		 TableColumn<Book,String> firstNameCol  = new TableColumn<>("First Name");
		 TableColumn<Book,String> lastNameCol  = new TableColumn<>("Last Name");
		 TableColumn<Book,String> telephoneCol  = new TableColumn<>("Telephone No.");

		 memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));
		 firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));		
		 lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));		
		 telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));		

		 memberIdCol.setSortType(TableColumn.SortType.DESCENDING);
		 
		 memberTableView.getColumns().setAll(memberIdCol, firstNameCol, lastNameCol, telephoneCol);
	     ObservableList data = FXCollections.observableList(memList);
	     memberTableView.setItems(data);
	}
	
	/* This class is a singleton */
	private AllMembersWindow() {}
	
	public void init() {
		grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("All Member IDs");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);
		
		ControllerInterface ci = new SystemController();
		HashMap<String, LibraryMember> memberHashMap = ci.allMemberIds();
		
		 memberTableView = new TableView<>();		
		 List<LibraryMember> memList = 	new ArrayList<>();
		 memList.addAll(memberHashMap.values());
		 populateTable(memList);
		 
		 VBox tableViewVBox = new VBox();
		 tableViewVBox.setPadding(new Insets(10, 10, 10, 10));;
		 tableViewVBox.getChildren().add(memberTableView);
               
		grid.add(tableViewVBox, 0,1);	
		
        NewStart.logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override 
        	public void handle(ActionEvent e) {
        		NewStart.resetWindow();
          	}
        });      
	}
}
