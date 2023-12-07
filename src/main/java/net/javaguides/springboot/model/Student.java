package net.javaguides.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student extends Human{
    
    public Student() {
		super();
	}
	
	public Student(String firstName, String lastName, String emailId) {
		super(firstName, lastName, emailId);
	}

}
