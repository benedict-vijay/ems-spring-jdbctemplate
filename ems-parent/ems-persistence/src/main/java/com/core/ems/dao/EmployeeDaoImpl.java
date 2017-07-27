package com.core.ems.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.core.ems.model.EmployeeEntity;

@Repository
public class EmployeeDaoImpl implements EmployeeDao 
{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<EmployeeEntity> getEmployeeList() {
		String sql = "SELECT * FROM ems_employee_detail";

		List<EmployeeEntity> employees = new ArrayList<EmployeeEntity>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) {
			EmployeeEntity empEntity = new EmployeeEntity();
			empEntity.setEmpId(Long.valueOf(Integer.toString((Integer)row.get("EMP_ID"))));
			empEntity.setEmpName((String)row.get("EMP_NAME"));
			empEntity.setSalary((BigDecimal)row.get("SALARY"));
			
			empEntity.setDateOfJoining((Date)row.get("DATE_OF_JOINING"));
			empEntity.setDateOfResign((Date)row.get("DATE_OF_RESIGN"));
			
			empEntity.setIsActive((String)row.get("IS_ACTIVE"));
			
			empEntity.setCreatedDate((Date)row.get("CREATED_DATE"));
			empEntity.setCreatedBy((String)row.get("CREATED_BY"));
			empEntity.setUpdatedDate((Date)row.get("UPDATED_ DATE"));
			empEntity.setUpdatedBy((String)row.get("UPDATED_BY"));

			employees.add(empEntity);
		}

		return employees;
	}

	@Override
	public EmployeeEntity getEmployeeById(Long empId) {
		String sql = "SELECT * FROM ems_employee_detail WHERE EMP_ID = ?";

		EmployeeEntity employeeEntity = (EmployeeEntity)jdbcTemplate.queryForObject(
				sql, new Object[] { empId },
				new BeanPropertyRowMapper<EmployeeEntity>(EmployeeEntity.class));

		return employeeEntity;
	}

	@Override
	public boolean insertEmployee(EmployeeEntity employee) {
		
		String query = "insert into ems_employee_detail (EMP_NAME, SALARY, DATE_OF_JOINING, DATE_OF_RESIGN, IS_ACTIVE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE) VALUES (?,?,?,?,?,?,?,?,?)";
		
		int rows = jdbcTemplate.update(query, new Object[] { employee.getEmpName(),employee.getSalary(),employee.getDateOfJoining(),employee.getDateOfResign(),
															 employee.getIsActive(),employee.getCreatedBy(),employee.getCreatedDate(),employee.getUpdatedBy(),employee.getUpdatedDate()});
		return rows > 0;
	}

	@Override
	public boolean updateEmployee(EmployeeEntity employee) {
		String query = "update ems_employee_detail set EMP_NAME = ?, SALARY = ?, DATE_OF_JOINING = ?, DATE_OF_RESIGN = ?, UPDATED_BY = ?, UPDATED_DATE = ? WHERE EMP_ID = ?";
		
		int rows = jdbcTemplate.update(query, new Object[] { employee.getEmpName(),employee.getSalary(),employee.getDateOfJoining(),employee.getDateOfResign(),
														     employee.getUpdatedBy(),employee.getUpdatedDate(),employee.getEmpId() });
		return rows > 0;
	}

	@Override
	public boolean deleteEmployee(EmployeeEntity employee) {
		String query = "delete from ems_employee_detail WHERE EMP_ID = ?";
		int rows = jdbcTemplate.update(query, new Object[] { employee.getEmpId() });
		return rows > 0;
	}

	
	
}