package application;

public class RentalBooks {
	int booksID;
	String author;
	String title;
	String genre;
	String UserID;
	String Name;
	String Surname;
	String Data;
	String Status;
	String Amount;
	String Availability;
	
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getAvailability() {
		return Availability;
	}
	public void setAvailability(String availability) {
		Availability = availability;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	int rentalID;
	
	public int getBooksID() {
		return booksID;
	}
	public void setBooksID(int booksID) {
		this.booksID = booksID;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSurname() {
		return Surname;
	}
	public void setSurname(String surname) {
		Surname = surname;
	}
	public int getRentalID() {
		return rentalID;
	}
	public void setRentalID(int rentalID) {
		this.rentalID = rentalID;
	}
	
	public RentalBooks(int booksID, String author, String title, String genre, String data, String userID, int rentalID,String status,String amount ,String availability) {
		super();
		this.booksID = booksID;
		this.author = author;
		this.title = title;
		this.genre = genre;
		this.Data = data;
		UserID = userID;
		this.rentalID = rentalID;
		this.Status = status;
		this.Amount = amount;
		this.Availability = availability;
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	
	
}
