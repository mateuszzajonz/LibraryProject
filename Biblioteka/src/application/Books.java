package application;

public class Books {

	int booksID;
	String author;
	String title;
	String availability;
	String amount;
	String genre;

	public int getbooksID() {
		return booksID;
	}

	public void setbooksID(int booksID) {
		this.booksID = booksID;
	}

	public String getauthor() {
		return author;
	}

	public void setautor(String author) {
		this.author = author;
	}

	public String gettitle() {
		return title;
	}

	public void settitle(String title) {
		this.title = title;
	}

	public String getavailability() {
		return availability;
	}

	public void setavailability(String availability) {
		this.availability = availability;
	}

	public String getamount() {
		return amount;
	}

	public void setamount(String amount) {
		this.amount = amount;
	}

	public String getgenre() {
		return genre;
	}

	public void setgenre(String genre) {
		this.genre = genre;
	}

	public Books() {
		super();
	}
	
	public Books(String author, String title, String amount, String genre) {
		super();
		this.author = author;
		this.title = title;
		this.amount = amount;
		this.genre = genre;
	}
	
	public Books(int booksID, String author, String title, String availability, String amount, String genre) {
		super();
		this.booksID = booksID;
		this.author = author;
		this.title = title;
		this.availability = availability;
		this.amount = amount;
		this.genre = genre;
	}

}
