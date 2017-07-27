package com.core.ems.service;

import java.util.List;
import java.util.Map;

public interface EmployeeService 
{
	List<Map<String, String>> getEmployeeList();
	Map<String, String> getEmployeeById(String id);
	Map<String, String> insertEmployee(Map<String, String> employee);
	Map<String, String> updateEmployee(Map<String, String> employee);
	Map<String, String> deleteEmployee(Map<String, String> map);
	
}
