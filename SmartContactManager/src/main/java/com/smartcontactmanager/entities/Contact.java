package com.smartcontactmanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	private String firstName;

	private String lastName;

	private String contact;

	private String address;

	private String state;

	private String city;

	private String zip;

	@Column(length = 5000)
	private String about;

	private String imageUrl;;

	private String categories;

	@ManyToOne
	private User user;

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contact(int cId, String firstName, String lastName, String contact, String address, String state,
			String city, String zip, String about, String imageUrl, String categories, User user) {
		super();
		this.cId = cId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		this.address = address;
		this.state = state;
		this.city = city;
		this.zip = zip;
		this.about = about;
		this.imageUrl = imageUrl;
		this.categories = categories;
		this.user = user;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

}
