package de.fom.kp.model;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.lang3.*;

import de.fom.kp.persistence.*;
import de.fom.kp.view.Message;

public class RegisterForm {
	
	private DateFormat dateFormat;
	private NumberFormat decimalFormat;
	private Integer id;
	private Gender gender;
	private String firstname;
	private String lastname;
	private String email;
	private String birthday;
	private String height;
	private Integer companyid;
	private String newcompany;
	private String comment;
	private boolean newsletter = false;
	private Set<Integer> interests;
	private List<Interest> interestList;
	
	public RegisterForm(DateFormat dateFormat, NumberFormat decimalFormat){
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
		interests = new HashSet<>();
	}
	
	public RegisterForm(Person p, DateFormat dateFormat, NumberFormat decimalFormat){
		this(dateFormat, decimalFormat);
		id = p.getId();
		gender = p.getGender();
		firstname = p.getFirstname();
		lastname = p.getLastname();
		email = p.getEmail();
		if(p.getBirthday()!=null){
			birthday = dateFormat.format(p.getBirthday());
		}
		if(p.getHeight()!=null){
			height =decimalFormat.format(p.getHeight());
		}
		companyid = p.getCompanyid();
		newsletter = p.isNewsletter();
		comment = p.getComment();
		interests = p.getInterests();
		System.out.println("");
	}
	
	public RegisterForm(HttpServletRequest request, DateFormat dateFormat, NumberFormat decimalFormat){
		this(dateFormat, decimalFormat);
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			id = Integer.parseInt(request.getParameter("id"));
		}
		if(StringUtils.isNotBlank(request.getParameter("gender"))){
			gender = Gender.valueOf(request.getParameter("gender"));
		}else{
			gender = Gender.U;
		}
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		email = request.getParameter("email");
		birthday = request.getParameter("birthday");
		height = request.getParameter("height");
		if(StringUtils.isNotBlank(request.getParameter("companyid"))){
			companyid = Integer.parseInt(request.getParameter("companyid"));
		}
		newcompany = request.getParameter("newcompany");
		if(request.getParameter("newsletter")!=null){
			newsletter = true;
		}
		comment = request.getParameter("comment");
		interests = new HashSet<>();
		String[] sa = request.getParameterValues("interests");
		if(sa!=null){
			for (String string : sa) {
				interests.add(Integer.parseInt(string));
			}
		}
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getNewcompany() {
		return newcompany;
	}

	public void setNewcompany(String newcompany) {
		this.newcompany = newcompany;
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

	public List<Interest> getInterestList() {
		return interestList;
	}

	public void setInterestList(List<Interest> interestList) {
		this.interestList = interestList;
	}

	public Person getPerson() {
		Person p = new Person();
		p.setId(id);
		p.setGender(gender);
		p.setFirstname(firstname);
		p.setLastname(lastname);
		p.setEmail(email);
		if(StringUtils.isNotBlank(birthday)){
			try {
				p.setBirthday(dateFormat.parse(birthday));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(height)){
			try {
				p.setHeight(decimalFormat.parse(height).doubleValue());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		p.setCompanyid(companyid);
		p.setNewsletter(newsletter);
		p.setComment(comment);
		p.setInterests(interests);
		return p;
	}

	public void validate(List<Message> errors) {
		if(StringUtils.isBlank(email)){
			errors.add(new Message("email", "Email required"));
		}
		try {
			if(StringUtils.isNotBlank(birthday)){
				dateFormat.parse(birthday);
			}
		} catch (ParseException e) {
			errors.add(new Message("birthday", "Birthday is not a Date"));
		}
		try {
			if(StringUtils.isNotBlank(height)){
				decimalFormat.parse(height);
			}
		} catch (ParseException e) {
			errors.add(new Message("height", "Height is not a Number"));
		}
	}
	
	
}
