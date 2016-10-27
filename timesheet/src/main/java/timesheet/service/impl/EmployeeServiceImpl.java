package timesheet.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import timesheet.dao.EmployeeDao;
import timesheet.domain.Employee;
import timesheet.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;
	
	@Override
	public List<Employee> getUnassignedEmployees() {
		List<Employee> employees = employeeDao.list();
	    Set<Employee> unassignedEmployees = new HashSet<Employee>();
	 
	    /*for (Employee employee : employees) {
	        if (!task.getAssignedEmployees().contains(employee)) {
	            unassignedEmployees.add(employee);
	        }
	    }*/
		return null;
	}

}
