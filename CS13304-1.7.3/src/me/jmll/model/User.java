package me.jmll.model;

public class User {
	/* 1. cuenta con tres atributos de tipo String: 
	 * username, password y fullName. Cree los 
	 * getters/setters y constructor con parámetros 
	 * username y password
	 * */
	String username = "";
	String password = "";
	String fullname = "";
	
	public User(String username2, String password2) {
		username = username2;
		password = password2;
	}
  
  public String getUsername() {
    return username;
  }

  public void setUsername(String newUsername) {
    this.username = newUsername;
  }
  
  public String getPassword(){
	  return password;
  }
  
  public void setPassword (String newPassword){
	  this.password = newPassword;
  }
  
  public String getFullname(){
	  return fullname;
  }
  
  public void setFullname(String newFullname){
	  this.fullname = newFullname;
  }
  
}
	  