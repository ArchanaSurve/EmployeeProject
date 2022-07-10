package com.employeeMicroservice.DemoEmployee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping("/getMsg/{name}")
	public String sayHello(@PathVariable String name)
	{
		String template="Hi team it's ";
		return template+name+" now we are starting project kick Off..";
	}
	
	@PostMapping("/createEmp")
	public Employee createEmployee(@RequestBody Employee employee) 
	{
		return employeeRepository.save(employee);
	}
	
	@PutMapping("/updateEmp")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee)
	{
		Optional<Employee> oldEmployee=employeeRepository.findById((employee).getEmployeeId());
		if(oldEmployee.isPresent()) 
		{
			return new ResponseEntity<>(employeeRepository.save(employee),HttpStatus.CREATED);
		}
		 
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/searchEmp/{id}")
	public Employee getEmployee(@PathVariable int id)
	{
		
		return CacheOperations.cache.get(id);
		//return employeeRepository.findById(id);
	}
	
	@GetMapping("/getAllEmps")
	public List<Employee> getAllEmloyees()
	{
		return employeeRepository.findAll();
	}
	
	@DeleteMapping("/deleteEmp/{id}")
	public String deleteEmployee(@PathVariable int id)
	{
		if(employeeRepository.findById(id).isPresent()) 
		{
			employeeRepository.deleteById(id);
			return "The Employee deleted successfully";
		}
		else 
		{
			return "The Employee with given id is not found";
		}
	}
	
}
