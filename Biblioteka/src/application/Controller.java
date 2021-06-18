package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sqlite.SQLiteDataSource;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.event.Event;
import javafx.scene.control.TableColumn;

public class Controller {

	@FXML
	public AnchorPane Login;
	public AnchorPane Library;
	public AnchorPane Register;
	public Tab Books;
	public Tab Users;
	public Tab Rental;
	public Tab Returns;
	public TabPane TabPanel;

	// Login
	public TextField Login_login;
	public TextField Login_password;
	public Button Login_btn;
	public Button Login_register;

	// Register
	public TextField Register_name;
	public TextField Register_surname;
	public TextField Register_email;
	public TextField Register_address;
	public TextField Register_password;
	public TextField Register_password2;
	public TextField Register_permissions;
	public Button Register_register;
	public Button Register_back;

	// Books
	public TextField Books_author;
	public TextField Books_title;
	public ComboBox<String> Books_type;
	public Button Books_search;
	public Button Books_clear;
	public TableView<Books> Books_tableview;
	public Button Books_rent;
	public Button Books_addbook;
	public Button Books_delbook;
	public Button Books_editbook;
	public TableColumn<Books, Number> Books_columnID;
	public TableColumn<Books, String> Books_columnAuthor;
	public TableColumn<Books, String> Books_columnTitle;
	public TableColumn<Books, String> Books_columnAvailability;
	public TableColumn<Books, String> Books_columnAmount;
	public TableColumn<Books, String> Books_columnGenre;

	// Users
	public TextField Users_name;
	public TextField Users_surname;
	public TextField Users_pesel;
	public TextField Users_miasto;
	public Button Users_search;
	public Button Users_clear;
	public Button Users_rent;
	public Button Users_adduser;
	public Button Users_deluser;
	public Button Users_edituser;
	public Button Users_rental;
	public TableView<String> Users_tableview;

	// Rental
	public TextField Rental_bid;
	public TextField Rental_author;
	public TextField Rental_title;
	public TextField Rental_type;
	public TextField Rental_uid;
	public TextField Rental_name;
	public TextField Rental_surname;
	public TextField Rental_pesel;
	public Button Rental_rent;

	// Returns
	public TextField Returns_id;
	public TextField Returns_name;
	public TextField Returns_surname;
	public TextField Returns_pesel;
	public TableView<String> Returns_tableview;
	public Button Returns_return;

	String login;
	SQLiteDataSource ds = null;
	ObservableList<User> obslist_login = FXCollections.observableArrayList();
	ObservableList<Books> obslist_books = FXCollections.observableArrayList();

	public void loadOnStart() {
		loadDB();
	}

	public void loadDB() {
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:LibraryDB.db");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void login(ActionEvent event) {
		try {
			Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement("Select Email,Password,Permissions from Users");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				obslist_login
						.add(new User(rs.getString("Email"), rs.getString("Password"), rs.getString("Permissions")));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}
		for (int i = 0; i < obslist_login.size(); i++) {
			if (Login_login.getText().equals(obslist_login.get(i).getEmail())
					&& Login_password.getText().equals(obslist_login.get(i).getPassword())
					&& obslist_login.get(i).getPermissions().equals("Admin")) {
				Login.setVisible(false);
				Library.setVisible(true);
				break;
			} else {
				TabPanel.getTabs().remove(1);
				TabPanel.getTabs().remove(1);
				TabPanel.getTabs().remove(1);
				Login.setVisible(false);
				Library.setVisible(true);
				break;
			}

		}
	}

	public void register(ActionEvent event) {
		try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO Users(Name,Surname,Email,Address,Password,Permissions) VALUES(?,?,?,?,?,?)");
			ps.setString(1, Register_name.getText());
			ps.setString(2, Register_surname.getText());
			ps.setString(3, Register_email.getText());
			ps.setString(4, Register_address.getText());
			ps.setString(5, Register_password.getText());
			ps.setString(6, Register_permissions.getText());
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}

	}

	public void registerChange(ActionEvent event) {
		Register.setVisible(true);
		Login.setVisible(false);
	}

	public void Books_Tab(Event event) {
		loadDB();
		Books_tableview.getItems().clear();
		try {
			Connection con = ds.getConnection();
			
			PreparedStatement pstmt = con.prepareStatement("Select * from Books");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				obslist_books.add(new Books(rs.getInt("ID"), rs.getString("Author"), rs.getString("Title"),
						rs.getString("Availability"), rs.getString("Amount"), rs.getString("Genre")));
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}

		try {
			Books_columnID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getbooksID()));
			Books_columnAuthor
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getauthor()));
			Books_columnTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().gettitle()));
			Books_columnAvailability
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getavailability()));
			Books_columnAmount
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getamount()));
			Books_columnGenre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getgenre()));
			Books_tableview.setItems(obslist_books);
		} catch (Exception e) {
			System.out.print("B³¹d" + e);
		}
	}
}
