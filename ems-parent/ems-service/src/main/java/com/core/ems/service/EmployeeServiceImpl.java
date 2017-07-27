package com.core.ems.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.ems.dao.EmployeeDao;
import com.core.ems.model.EmployeeEntity;
import com.core.ems.util.DateUtil;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	@Transactional
	public List<Map<String, String>> getEmployeeList() {
		return getEmployeeList(employeeDao.getEmployeeList());
	}

	@Override
	@Transactional
	public Map<String, String> getEmployeeById(String id) {
		return entityToMap(employeeDao.getEmployeeById(Long.valueOf(id)));
	}

	@Override
	@Transactional
	public Map<String, String> insertEmployee(Map<String, String> employeeMap) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("success", "true");
		
		EmployeeEntity employeeEntity = new EmployeeEntity();
		
		try {
			employeeEntity.setCreatedDate(new Date());
			employeeEntity.setCreatedBy("ADMIN");
			employeeEntity.setIsActive("1");
			boolean success = employeeDao.insertEmployee(mapToEntity(employeeMap, employeeEntity));
			resultMap.put("success", Boolean.toString(success));
			resultMap.put("message", "Employee added successfully !!");
			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			resultMap.put("success", "false");
		}
		
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, String> updateEmployee(Map<String, String> employeeMap) {

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("success", "true");

		try {
			EmployeeEntity employeeEntity = employeeDao.getEmployeeById(Long.valueOf(employeeMap.get("empId")));
			if(employeeEntity != null) {

				employeeEntity.setUpdatedBy("ADMIN");
				employeeEntity.setUpdatedDate(new Date());
				
				boolean success = employeeDao.updateEmployee(mapToEntity(employeeMap, employeeEntity));
				resultMap.put("success", Boolean.toString(success));
				resultMap.put("message", "Employee updated successfully !!");
			} else {
				resultMap.put("success", "false");
				resultMap.put("message", "employee does not exist for id <" + employeeMap.get("empId") + ">");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			resultMap.put("success", "false");
		}
		
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, String> deleteEmployee(Map<String, String> map) {

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("success", "true");

		try {
			EmployeeEntity employeeEntity = employeeDao.getEmployeeById(Long.valueOf(map.get("empId")));
			if(employeeEntity != null) {

				boolean success = employeeDao.deleteEmployee(employeeEntity);
				resultMap.put("success", Boolean.toString(success));
				resultMap.put("message", "Employee deleted successfully !!");
				System.out.println("\n\nresultMap : "+ resultMap + " \n\n");
			} else {
				resultMap.put("success", "false");
				resultMap.put("message", "employee does not exist for id <" + map.get("empId") + ">");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			resultMap.put("success", "false");
		}
		
		return resultMap;
	}

	private List<Map<String, String>> getEmployeeList(List<EmployeeEntity> employeeEntities) {
		List<Map<String, String>> empList = new ArrayList<Map<String,String>>();
		if(employeeEntities != null) {
			
			for (EmployeeEntity employeeEntity : employeeEntities) {
				empList.add(entityToMap(employeeEntity));
			}
		}
		return empList;
	}
	
	private Map<String, String> entityToMap(EmployeeEntity employeeEntity){
		
		Map<String, String> map = new HashMap<String, String>();
		if(employeeEntity != null) {
			map.put("empId", String.valueOf(employeeEntity.getEmpId()));
			map.put("empName", employeeEntity.getEmpName());
			map.put("empSalary", employeeEntity.getSalary().toPlainString());
			map.put("empDOJ", DateUtil.getFormattedDateString(employeeEntity.getDateOfJoining()));
			map.put("empDOR", DateUtil.getFormattedDateString(employeeEntity.getDateOfResign()));
			map.put("empActive", Boolean.toString(employeeEntity.getIsActive().equals("1")));
		} 
		
		return map;
	}

	private EmployeeEntity mapToEntity(Map<String, String> employeeMap, EmployeeEntity employee){
		
		if(employeeMap != null && employee != null) {
			employee.setEmpName(employeeMap.get("empName"));
			employee.setSalary(new BigDecimal(employeeMap.get("empSalary")));
			employee.setDateOfJoining(DateUtil.getDate(employeeMap.get("empDOJ")));
			employee.setDateOfResign(DateUtil.getDate(employeeMap.get("empDOR")));
		}
		
		return employee;
	}

	
}
