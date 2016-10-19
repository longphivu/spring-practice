package timesheet.service;

import java.util.List;

import timesheet.domain.Employee;

/**
 * Business that defines operations on employee
 */
public interface EmployeeService {
	List<Employee> getUnassignedEmployees();
}
