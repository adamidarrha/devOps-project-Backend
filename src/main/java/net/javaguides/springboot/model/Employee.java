package net.javaguides.springboot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee extends Human{
	
	public Employee() {
		super();
	}
	
	public Employee(String firstName, String lastName, String emailId) {
		super(firstName, lastName, emailId);
	}
	
}
