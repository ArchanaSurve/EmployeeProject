package com.employeeMicroservice.DemoEmployee;

import java.util.HashMap;
import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheOperations {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public static HashMap<Integer,Employee> cache=new HashMap<>();
	public List<Employee> employeeList;
	
	@Scheduled(cron="* */2 * * * *")
	public void loadCache() {
		System.out.println("load started");
		employeeList=employeeRepository.findAll();
		if(!employeeList.isEmpty()) 
		{
			employeeList.forEach(employee ->cache.put(employee.getEmployeeId(),employee));
		}
	}
	
}
