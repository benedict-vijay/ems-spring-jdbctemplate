package com.core.ems.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.core.ems.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController
{
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/getEmployeeList.rest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Map<String,String>> getEmployeeList() {
		List<Map<String, String>> employeeList = employeeService.getEmployeeList();
        return employeeList;
    }

	@RequestMapping(value = "/getEmployeeById.rest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String,String> getEmployeeList(@RequestParam("empId") String empId) {
		Map<String, String> employee = employeeService.getEmployeeById(empId);
        return employee;
    }

	@RequestMapping(value="/insertEmployee.rest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody Map<String, String> insertEmployee(@RequestBody Map<String, String> employee) {
		Map<String, String> employeeStatus = employeeService.insertEmployee(employee);
        return employeeStatus;
	}

	@RequestMapping(value="/updateEmployee.rest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody Map<String, String> updateEmployee(@RequestBody Map<String, String> employee) {
		Map<String, String> employeeStatus = employeeService.updateEmployee(employee);
        return employeeStatus;
	}

	@RequestMapping(value="/deleteEmployee.rest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody Map<String, String> deleteEmployee(@RequestBody Map<String, String> employee) {
		Map<String, String> employeeStatus = employeeService.deleteEmployee(employee);
        return employeeStatus;
	}

}