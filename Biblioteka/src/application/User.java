package application;

public class User {
	String UserID;
	String Name;
	String Surname;
	String Email;
	String Pesel;
	String Address;
	String Password;
	String Permissions;
	
	public String getPesel() {
		return Pesel;
	}
	public void setPesel(String pesel) {
		this.Pesel = pesel;
	}
	public String getUserID() {
		return UserID;
	}
	public String getPermissions() {
		return Permissions;
	}
	public void setPermissions(String permissions) {
		this.Permissions = permissions;
	}
	public void setUserID(String userID) {
		this.UserID = userID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public String getSurname() {
		return Surname;
	}
	public void setSurname(String surname) {
		this.Surname = surname;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public User(String email) {
		super();
		this.Email = email;
	}
	public User(String email, String password,String permissions) {
		super();
		this.Email = email;
		this.Password = password;
		this.Permissions = permissions;
	}
	public User(String userID, String name, String surname, String pesel, String address) {
		super();
		this.UserID = userID;
		this.Name = name;
		this.Surname = surname;
		this.Pesel = pesel;
		this.Address = address;
	}

	
}
