package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Student;
import net.javaguides.springboot.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class StudentController {

	@Autowired
	private StudentRepository StudentRepository;
	
	// get all Students
	@GetMapping("/students")
	public List<Student> getAllStudents(){
		return StudentRepository.findAll();
	}		
	
	// create Student rest api
	@PostMapping("/students")
	public Student createStudent(@RequestBody Student Student) {
		return StudentRepository.save(Student);
	}
	
	// get Student by id rest api
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
		Student Student = StudentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not exist with id :" + id));
		return ResponseEntity.ok(Student);
	}
	
	// update Student rest api
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student StudentDetails){
		Student Student = StudentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not exist with id :" + id));
		
		Student.setFirstName(StudentDetails.getFirstName());
		Student.setLastName(StudentDetails.getLastName());
		Student.setEmailId(StudentDetails.getEmailId());
		
		Student updatedStudent = StudentRepository.save(Student);
		return ResponseEntity.ok(updatedStudent);
	}
	
	// delete Student rest api
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id){
		Student Student = StudentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not exist with id :" + id));
		
		StudentRepository.delete(Student);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
