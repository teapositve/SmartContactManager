package com.smart.entitites;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotBlank(message = "Fields cannot be blank !!")
	@Size(min = 2, max = 20, message = "Min 2 and Max 20 characters are allowed")
	private String name;
	
	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message="Invalid Email !!")
	private String email;
	
	@NotNull(message = "Password field cannot be blank !!")
	@Column(length = 1000)
//	@NotBlank(message = "Password field cannot be blank !!")
//	@Size(min = 8, max = 20, message = "The len of password should be min 8 characters in which it must consists 1 Upper letter, 1 number, special character")
	private String password;
	
	private String role;
	
	@Column(length = 1000)
	@Size(min = 5, max = 500, message = "Min 20 and Max 500 characters are allowed")
	private String about;
	private String imageUrl;
	
	private boolean status;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private java.util.List<Contact> contacts = new ArrayList();
	
	
	//Non parameterized Constructor
	public User() {
		super();
	}
	
	//Getter and Setter
	public int getId() { 
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public java.util.List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(java.util.List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", about=" + about + ", imageUrl=" + imageUrl + ", status=" + status + ", contacts=" + contacts + "]";
	}
	
	
	
}


