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
import net.javaguides.springboot.model.Teacher;
import net.javaguides.springboot.repository.TeacherRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class TeacherController {

	@Autowired
	private TeacherRepository TeacherRepository;
	
	// get all Teachers
	@GetMapping("/teachers")
	public List<Teacher> getAllTeachers(){
		return TeacherRepository.findAll();
	}		
	
	// create Teacher rest api
	@PostMapping("/teachers")
	public Teacher createTeacher(@RequestBody Teacher Teacher) {
		return TeacherRepository.save(Teacher);
	}
	
	// get Teacher by id rest api
	@GetMapping("/teachers/{id}")
	public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
		Teacher Teacher = TeacherRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher not exist with id :" + id));
		return ResponseEntity.ok(Teacher);
	}
	
	// update Teacher rest api
	
	@PutMapping("/teachers/{id}")
	public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher TeacherDetails){
		Teacher Teacher = TeacherRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher not exist with id :" + id));
		
		Teacher.setFirstName(TeacherDetails.getFirstName());
		Teacher.setLastName(TeacherDetails.getLastName());
		Teacher.setEmailId(TeacherDetails.getEmailId());
		
		Teacher updatedTeacher = TeacherRepository.save(Teacher);
		return ResponseEntity.ok(updatedTeacher);
	}
	
	// delete Teacher rest api
	@DeleteMapping("/teachers/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTeacher(@PathVariable Long id){
		Teacher Teacher = TeacherRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher not exist with id :" + id));
		
		TeacherRepository.delete(Teacher);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
