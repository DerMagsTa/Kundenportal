package de.fom.kp.persistence;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {

	private Integer id;
	private Gender gender;
	private String firstname;
	private String lastname;
	private String email;
	private Date birthday;
	private Double height;
	private Integer companyid;
	private String comment;
	private boolean newsletter = false;
	private Set<Integer> interests = new HashSet<>();

	public Person() {
	}

	public Person(String email) {
		this.email = email;
	}
	
	public Person(Integer id, String email, String firstname, String lastname, Date birthday) {
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthday = birthday;
	}

	public Person(Integer id, Gender gender, String firstname, String lastname, String email, Date birthday, Double height,
			Integer companyid, String comment, boolean newsletter) {
		this.id = id;
		this.gender = gender;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthday = birthday;
		this.height = height;
		this.companyid = companyid;
		this.comment = comment;
		this.newsletter = newsletter;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isNewsletter() {
		return newsletter;
	}

	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}

	public Set<Integer> getInterests() {
		return interests;
	}

	public void setInterests(Set<Integer> interests) {
		this.interests = interests;
	}
	
	
}
