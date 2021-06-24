package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.sqlite.SQLiteDataSource;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class NewWindowController implements Initializable {

	public TextField Books_NewWindow_author;
	public TextField Books_NewWindow_title;
	public TextField Books_NewWindow_amount;
	public TextField Books_NewWindow_genre;
	@FXML
	private Button Books_NewWindow_set;

	SQLiteDataSource ds = null;
	String title;
	private static boolean thrownExcep;
	static Books book = new Books();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(200);
					Books_NewWindow_load();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start();
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

	public void Books_NewWindow_Set(ActionEvent event) {
		loadDB();
		int rowNr = 0;
		thrownExcep = false;
		Stage stage = (Stage) Books_NewWindow_set.getScene().getWindow();
		String title = stage.getTitle();
		if (!Books_NewWindow_author.getText().equals("") && !Books_NewWindow_title.getText().equals("")
				&& !Books_NewWindow_amount.getText().equals("") && !Books_NewWindow_genre.getText().equals(""))
			if (title.equals("Edytuj")) {
				try {
					Connection con = ds.getConnection();
					PreparedStatement ps = con.prepareStatement("UPDATE Books SET Author = '"
							+ Books_NewWindow_author.getText() + "', Title = '" + Books_NewWindow_title.getText()
							+ "', Availability = '" + Amount(Books_NewWindow_amount.getText(), false) + "', Amount = '"
							+ Amount(Books_NewWindow_amount.getText(), true) + "', Genre = '"
							+ Books_NewWindow_genre.getText() + "' WHERE ID = ?;");
					ps.setInt(1, book.getbooksID());

					if (!thrownExcep)
						ps.executeUpdate();
					ps.close();
					con.close();
					stage.close();
				} catch (SQLException e) {
					System.out.print("B³¹d" + e);
				}
			} else {
				try {
					Connection con = ds.getConnection();
					PreparedStatement pstmt = con.prepareStatement("Select MAX(ID) AS Max from Books");
					ResultSet rs = pstmt.executeQuery();
					rowNr = rs.getInt("Max");
					rs.close();
					con.close();
				} catch (SQLException e) {
					System.out.print("B³¹d" + e);
				}
				try {
					Connection con = ds.getConnection();
					PreparedStatement ps = con.prepareStatement(
							"INSERT INTO Books(ID,Author,Title, Availability, Amount, Genre)" + " VALUES(?,?,?,?,?,?)");
					ps.setInt(1, rowNr + 1);
					ps.setString(2, Books_NewWindow_author.getText());
					ps.setString(3, Books_NewWindow_title.getText());
					ps.setString(4, Amount(Books_NewWindow_amount.getText(), false));
					ps.setString(5, Amount(Books_NewWindow_amount.getText(), true));
					ps.setString(6, Books_NewWindow_genre.getText());
					if (!thrownExcep)
						ps.executeUpdate();
					ps.close();
					con.close();
					stage.close();
				} catch (SQLException e) {
					System.out.print("B³¹d" + e);
				}
			}
			Main.myConrollerToPass.BooksDB();
	}

	public String Amount(String amount, boolean pass) {
		String[] word = amount.split("\\/");
		try {
		if (Integer.parseInt(word[0]) <= Integer.parseInt(word[1]) && pass)
			amount = word[0] + "/" + word[1];
		else if (!pass) {
			if (Integer.parseInt(word[0]) == 0)
				amount = "Brak";
			else
				amount = "Dostêpne";
		} else
			thrownExcep = true;
		}catch(Exception e)
		{
			System.out.print("B³¹d" + e);
			thrownExcep = true;
		}
		return amount;
	}

	public void Books_NewWindow_load() {
		try {
			Stage stage = (Stage) Books_NewWindow_set.getScene().getWindow();
			book = (Books) stage.getUserData();
			Books_NewWindow_author.setText(book.getauthor());
			Books_NewWindow_title.setText(book.gettitle());
			Books_NewWindow_amount.setText(book.getamount());
			Books_NewWindow_genre.setText(book.getgenre());
		} catch (Exception e) {
			System.out.println("B³¹d: " + e);
		}
	}
}
