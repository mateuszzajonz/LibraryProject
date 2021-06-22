package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.sqlite.SQLiteDataSource;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.event.Event;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Controller {

	@FXML
	public AnchorPane Login;
	public AnchorPane Library;
	public AnchorPane Register;
	public AnchorPane EditAdd;
	public Tab Books;
	public Tab Users;
	public Tab Rental;
	public Tab Logout;
	public Tab Returns;
	public TabPane TabPanel;

	// Login
	public TextField Login_login;
	public PasswordField Login_password;
	public Button Login_btn;
	public Button Login_register;

	// Register
	public TextField Register_name;
	public TextField Register_surname;
	public TextField Register_email;
	public TextField Register_address;
	public TextField Register_password;
	public TextField Register_password2;
	public TextField Register_pesel;
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
	public HBox Books_hboxAdmin;
	public HBox Books_hboxUser;
	public ComboBox<String> Users_field;
	public TextField Users_text;
	public Button Users_search;
	public Button Users_clear;
	public Button Users_rent;
	public Button Users_adduser;
	public Button Users_deluser;
	public Button Users_edituser;
	public Button Users_rental;
	public TableView<User> Users_tableview;
	public TableColumn<User, String> NrCol;
	public TableColumn<User, String> SurnameCol;
	public TableColumn<User, String> NameCol;
	public TableColumn<User, String> PeselCol;
	public TableColumn<User, String> AddressCol;
	public TableColumn<User, String> PermissionCol;
	public TableColumn<User, String> EmailCol;
	public TableColumn<User, String> PasswordCol;
	ObservableList<User> obslist_select_users = FXCollections.observableArrayList();
	ObservableList<User> obslist_users = FXCollections.observableArrayList();

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

	// Add Edit
	public TextField Add_name;
	public TextField Add_surname;
	public TextField Add_email;
	public TextField Add_address;
	public TextField Add_password;
	public TextField Add_pesel;
	public ComboBox<String> Add_permissions;
	public Button Add_btn;
	public Button Edit_btn;
	ObservableList<User> obslist_add = FXCollections.observableArrayList();

	Boolean Add_added = false;
	Boolean blad = false;
	String login;
	Boolean deleted = false;
	SQLiteDataSource ds = null;
	ObservableList<User> obslist_login = FXCollections.observableArrayList();
	ObservableList<Books> obslist_books = FXCollections.observableArrayList();
	ObservableList<User> obslist_register = FXCollections.observableArrayList();

	public void loadOnStart() {
		loadDB();
		Add_permissions.getItems().addAll("Admin", "User");
		Users_field.getItems().addAll("Imie", "Nazwisko", "Pesel", "Miasto");
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
		blad = false;
		obslist_login.clear();
		try {
			Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement("Select Email,Password,Permissions from Users");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				obslist_login
						.add(new User(rs.getString("Email"), rs.getString("Password"), rs.getString("Permissions")));
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}
		for (int i = 0; i < obslist_login.size(); i++) {
			if (Login_login.getText().equals(obslist_login.get(i).getEmail())
					&& Login_password.getText().equals(obslist_login.get(i).getPassword())
					&& obslist_login.get(i).getPermissions().equals("Admin")) {
				UserTableView();
				Library.setVisible(true);
				Login.setVisible(false);
				TabPanel.getSelectionModel().select(Books);
				if(deleted) {
				TabPanel.getTabs().remove(Logout);
				TabPanel.getTabs().add(Users);
				TabPanel.getTabs().add(Rental);
				TabPanel.getTabs().add(Returns);
				TabPanel.getTabs().add(Logout);
				deleted = false;
				}
				Books_hboxUser.setVisible(false);
				Books_hboxAdmin.setVisible(true);
				blad = false;
				break;
			} else if (Login_login.getText().equals(obslist_login.get(i).getEmail())
					&& Login_password.getText().equals(obslist_login.get(i).getPassword())
					&& !obslist_login.get(i).getPermissions().equals("Admin")) {
				TabPanel.getSelectionModel().select(Books);
				TabPanel.getTabs().remove(Users);
				TabPanel.getTabs().remove(Rental);
				TabPanel.getTabs().remove(Returns);
				Books_hboxUser.setVisible(true);
				Books_hboxAdmin.setVisible(false);
				Login.setVisible(false);
				Library.setVisible(true);
				deleted = true;
				blad = false;
				break;
			} else {
				blad = true;
			}
		}
		if (blad) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Nie ma takiego profilu");
			alert.showAndWait();
		}
	}

	public void register(ActionEvent event) {
		blad=false;
		try {

			Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement("Select Email from Users");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				obslist_register.add(new User(rs.getString("Email")));
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}
		for (int i = 0; i < obslist_register.size(); i++) {
			if (Register_email.getText().equals(obslist_register.get(i).getEmail())) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Taki email ju¿ istnieje");
				alert.showAndWait();
				blad = true;
				break;
			}
		}
		if(Register_pesel.getLength() != 11) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Z³y pesel");
			alert.showAndWait();
			blad= true;
		}
		if (!blad) {
			if (Register_password.getText().equals(Register_password2.getText())
					&& Register_password.getLength() >= 8) {
				try {
					Connection con = ds.getConnection();
					PreparedStatement ps = con.prepareStatement(
							"INSERT INTO Users(Name,Surname,Email,Address,Password,Permissions,Pesel) VALUES(?,?,?,?,?,?,?)");
					ps.setString(1, Register_name.getText());
					ps.setString(2, Register_surname.getText());
					ps.setString(3, Register_email.getText());
					ps.setString(4, Register_address.getText());
					ps.setString(5, Register_password.getText());
					ps.setString(6, "user");
					ps.setString(7, Register_pesel.getText());
					ps.executeUpdate();
					ps.close();
					con.close();
				} catch (SQLException e) {
					System.out.print("B³¹d" + e);
				}
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Pomyœlnie siê zarejestrowano");
				alert.showAndWait();
				Register_name.clear();
				Register_surname.clear();
				Register_email.clear();
				Register_address.clear();
				Register_password.clear();
				Register_password2.clear();
				Register_pesel.clear();
				goBack();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Z³e has³o lub has³o ma mniej ni¿ 8 znaków");
				alert.showAndWait();
			}
		}
	}

	public void registerChange(ActionEvent event) {
		Register.setVisible(true);
		Login.setVisible(false);
	}

	public void goBack() {
		Register.setVisible(false);
		Login.setVisible(true);
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

	public void UserTableView() {
		obslist_users.clear();
		try {
			Connection con = ds.getConnection();
			ResultSet rs = con.createStatement()
					.executeQuery("Select UserID,Name,Surname,Email,Pesel,Address,Password,Permissions FROM Users");
			while (rs.next()) {
				obslist_users.add(new User(rs.getString("UserID"), rs.getString("Name"), rs.getString("Surname"),
						rs.getString("Email"), rs.getString("Pesel"), rs.getString("Address"), rs.getString("Password"),
						rs.getString("Permissions")));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}

		NrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserID()));
		SurnameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
		NameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		PeselCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPesel()));
		AddressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
		PermissionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPermissions()));
		PasswordCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
		EmailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

		Users_tableview.setItems(obslist_users);
	}

	public void Logout() {
		ButtonType foo = new ButtonType("OK", ButtonData.OK_DONE);
		ButtonType bar = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.WARNING,
		        "Czy napewno chcesz siê wylogowaæ?",
		        foo,
		        bar);

		alert.setTitle("Date format warning");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.orElse(bar) == foo) {
			TabPanel.getSelectionModel().select(Books);
			Login.setVisible(true);
			Library.setVisible(false);
		}
		else {
			TabPanel.getSelectionModel().select(Books);
		}

	}
	// Users
	public void Delete_User() {
		try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM Users WHERE UserID = ?");
			User user = Users_tableview.getSelectionModel().getSelectedItem();
			ps.setString(1, user.getUserID());
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		} catch (NullPointerException e) {

		}
		UserTableView();
	}

	public void Select_User() {
		try {
			obslist_select_users.clear();
			ResultSet rs = null;
			Connection con = ds.getConnection();
			if (!Users_text.getText().equals("")) {
				switch (Users_field.getValue()) {
				case "Imie":
					rs = con.createStatement()
							.executeQuery("Select UserID,Name,Surname,Email,Pesel,Address,Password,Permissions FROM Users WHERE Name = '"
									+ Users_text.getText() + "'");
					break;
				case "Nazwisko":
					rs = con.createStatement()
							.executeQuery("Select UserID,Name,Surname,Email,Pesel,Address,Password,Permissions FROM Users WHERE Surname = '"
									+ Users_text.getText() + "'");
					break;
				case "Pesel":
					rs = con.createStatement()
							.executeQuery("Select UserID,Name,Surname,Email,Pesel,Address,Password,Permissions FROM Users WHERE Pesel = '"
									+ Users_text.getText() + "'");
					break;
				case "Miasto":
					rs = con.createStatement()
							.executeQuery("Select UserID,Name,Surname,Email,Pesel,Address,Password,Permissions FROM Users WHERE Address LIKE '%"
									+ Users_text.getText() + "%'");
					break;
				}
				while (rs.next()) {
					obslist_select_users.add(new User(rs.getString("UserID"), rs.getString("Name"), rs.getString("Surname"),
							rs.getString("Email"), rs.getString("Pesel"), rs.getString("Address"), rs.getString("Password"),
							rs.getString("Permissions")));
				}
				rs.close();
				con.close();

				NrCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserID()));
				SurnameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
				NameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
				PeselCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPesel()));
				AddressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
				PermissionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPermissions()));
				PasswordCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
				EmailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
				
				Users_tableview.setItems(obslist_select_users);
			} else {
				UserTableView();
			}
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		} catch (NullPointerException e) {
			UserTableView();
		}
	}

	public void Add_Btn_User() {
		Library.setVisible(false);
		EditAdd.setVisible(true);
		Add_btn.setVisible(true);
		Edit_btn.setVisible(false);
		Add_name.clear();
		Add_surname.clear();
		Add_email.clear();
		Add_pesel.clear();
		Add_address.clear();
		Add_password.clear();
	}

	public void Add_User(ActionEvent event) {
		blad = false;
		try {
			loadDB();
			Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement("Select Email from Users");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				obslist_add.add(new User(rs.getString("Email")));
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}
		for (int i = 0; i < obslist_add.size(); i++) {
			if (Add_email.getText().equals(obslist_add.get(i).getEmail())) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Taki email ju¿ istnieje");
				alert.showAndWait();
				blad = true;
				break;
			}
		}
		if(Register_pesel.getLength() != 11) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Z³y pesel");
			alert.showAndWait();
			blad= true;
		}
		if (!blad) {
			if (Add_password.getLength() >= 8) {
				try {
					Connection con = ds.getConnection();
					PreparedStatement ps = con.prepareStatement(
							"INSERT INTO Users(Name,Surname,Email,Address,Password,Permissions,Pesel) VALUES(?,?,?,?,?,?,?)");
					ps.setString(1, Add_name.getText());
					ps.setString(2, Add_surname.getText());
					ps.setString(3, Add_email.getText());
					ps.setString(4, Add_address.getText());
					ps.setString(5, Add_password.getText());
					ps.setString(6, Add_permissions.getValue());
					ps.setString(7, Add_pesel.getText());
					ps.executeUpdate();
					ps.close();
					con.close();
				} catch (SQLException e) {
					System.out.print("B³¹d" + e);
				}
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Pomyœlnie dodano");
				alert.showAndWait();
				UserTableView();
				Library.setVisible(true);
				EditAdd.setVisible(false);
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Z³e has³o lub has³o ma mniej ni¿ 8 znaków");
				alert.showAndWait();
			}
		}
	}

	public void Edit_User() {
		blad = false;
		if (Add_password.getLength() >= 8) {
			try {
				Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement("UPDATE Users SET Name = '" + Add_name.getText()
						+ "', Surname = '" + Add_surname.getText() + "',Email = '" + Add_email.getText()
						+ "',Address = '" + Add_address.getText() + "',Password = '" + Add_password.getText()
						+ "',Permissions = '" + Add_permissions.getValue() + "',Pesel = '" + Add_pesel.getText()
						+ "' WHERE UserID = " + Users_tableview.getSelectionModel().getSelectedItem().getUserID() + "");
				ps.executeUpdate();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.print("B³¹d" + e);
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Pomyœlnie edytowano");
			alert.showAndWait();
			Library.setVisible(true);
			EditAdd.setVisible(false);
			UserTableView();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Z³e has³o lub has³o ma mniej ni¿ 8 znaków");
			alert.showAndWait();
		}
	}

	public void Clear_User(ActionEvent event) {
		Users_text.clear();
		Users_field.valueProperty().set(null);
	}

	public void Edit_Btn_User(ActionEvent event) {
		Library.setVisible(false);
		EditAdd.setVisible(true);
		Add_btn.setVisible(false);
		Edit_btn.setVisible(true);
		Add_name.setText(Users_tableview.getSelectionModel().getSelectedItem().getName());
		Add_surname.setText(Users_tableview.getSelectionModel().getSelectedItem().getSurname());
		Add_email.setText(Users_tableview.getSelectionModel().getSelectedItem().getEmail());
		Add_permissions.setValue(Users_tableview.getSelectionModel().getSelectedItem().getPermissions());
		Add_pesel.setText(Users_tableview.getSelectionModel().getSelectedItem().getPesel());
		Add_address.setText(Users_tableview.getSelectionModel().getSelectedItem().getAddress());
		Add_password.setText(Users_tableview.getSelectionModel().getSelectedItem().getPassword());
	}

	public void Rentals_User(ActionEvent event) {
		TabPanel.getSelectionModel().select(Returns);
		Returns_id.setText(Users_tableview.getSelectionModel().getSelectedItem().getUserID());
		Returns_name.setText(Users_tableview.getSelectionModel().getSelectedItem().getName());
		Returns_surname.setText(Users_tableview.getSelectionModel().getSelectedItem().getSurname());
		Returns_pesel.setText(Users_tableview.getSelectionModel().getSelectedItem().getPesel());

	}

	public void Retnal_User(ActionEvent event) {
		TabPanel.getSelectionModel().select(Rental);
		Rental_uid.setText(Users_tableview.getSelectionModel().getSelectedItem().getUserID());
		Rental_name.setText(Users_tableview.getSelectionModel().getSelectedItem().getName());
		Rental_surname.setText(Users_tableview.getSelectionModel().getSelectedItem().getSurname());
		Rental_pesel.setText(Users_tableview.getSelectionModel().getSelectedItem().getPesel());
	}
}
