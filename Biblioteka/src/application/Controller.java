package application;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sqlite.SQLiteDataSource;

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
	
	//Login
	public TextField Login_login;
	public TextField Login_password;
	public Button Login_btn;
	public Button Login_register;
	
	//Register
	public TextField Register_name;
	public TextField Register_surname;
	public TextField Register_email;
	public TextField Register_password;
	public TextField Register_password2;
	public Button Register_register;
	public Button Register_back;
	
	//Books
	public TextField Books_autor;
	public TextField Books_title;
	public ComboBox<String> Books_type;
	public Button Books_search;
	public Button Books_clear;
	public TableView<String> Books_tableview;
	public Button Books_rent;
	public Button Books_addbook;
	public Button Books_delbook;
	public Button Books_editbook;
	
	//Users
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
	
	//Rental
	public TextField Rental_bid;
	public TextField Rental_autor;
	public TextField Rental_title;
	public TextField Rental_type;
	public TextField Rental_uid;
	public TextField Rental_name;
	public TextField Rental_surname;
	public TextField Rental_pesel;
	public Button Rental_rent;
	
	//Returns
	public TextField Returns_id;
	public TextField Returns_name;
	public TextField Returns_surname;
	public TextField Returns_pesel;
	public TableView<String> Returns_tableview;
	public Button Returns_return;
	
	String login;
	SQLiteDataSource ds = null;
	ObservableList<User> obslist = FXCollections.observableArrayList();
//	
	public void loadOnStart() {
		loadDB();
	}
	public void loadDB() {
        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:LibraryDB.db");
        } catch ( Exception e ) {
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
				obslist.add(new User(rs.getString("Email"),rs.getString("Password"),rs.getString("Permissions")));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}
		for(int i=0;i<obslist.size();i++) {
			if(Login_login.getText().equals(obslist.get(i).getEmail()) && Login_password.getText().equals(obslist.get(i).getPassword()) && obslist.get(i).getPermissions().equals("Admin")) {
				Login.setVisible(false);
				Library.setVisible(true);
				break;
			}else {
				TabPanel.getTabs().remove(1);
				TabPanel.getTabs().remove(1);
				TabPanel.getTabs().remove(1);
				Login.setVisible(false);
				Library.setVisible(true);
				break;
			}
			
		}

	}
}
