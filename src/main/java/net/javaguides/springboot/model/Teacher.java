package net.javaguides.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher extends Human{
    
    public Teacher() {
		super();
	}
	
	public Teacher(String firstName, String lastName, String emailId) {
		super(firstName, lastName, emailId);
	}

}
