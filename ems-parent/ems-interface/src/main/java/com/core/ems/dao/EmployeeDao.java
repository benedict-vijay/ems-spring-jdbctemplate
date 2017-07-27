package com.core.ems.dao;

import java.util.List;

import com.core.ems.model.EmployeeEntity;

public interface EmployeeDao 
{
	List<EmployeeEntity> getEmployeeList();
	EmployeeEntity getEmployeeById(Long empId);
	boolean insertEmployee(EmployeeEntity employee);
	boolean updateEmployee(EmployeeEntity employee);
	boolean deleteEmployee(EmployeeEntity employee);
}