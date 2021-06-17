package application;

public class User {
	int UserID;
	String Name;
	String Surname;
	String Email;
	String Address;
	String Password;
	String Permissions;
	
	public int getUserID() {
		return UserID;
	}
	public String getPermissions() {
		return Permissions;
	}
	public void setPermissions(String permissions) {
		Permissions = permissions;
	}
	public void setUserID(int userID) {
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
	public User(String email, String password,String permissions) {
		super();
		this.Email = email;
		this.Password = password;
		this.Permissions = permissions;
	}
	
}
