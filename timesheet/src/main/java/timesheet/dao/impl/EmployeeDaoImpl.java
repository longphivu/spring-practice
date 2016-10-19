package timesheet.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import timesheet.dao.EmployeeDao;
import timesheet.domain.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl extends HibernateDao<Employee, Long> implements EmployeeDao {

	@Override
	public boolean removeEmployee(Employee employee) {
		Query employeeTaskQuery = currentSession()
				.createQuery("from Task t where :id in elements(t.assignedEmployees)");
		employeeTaskQuery.setParameter("id", employee.getId());
		//employee mustn't be assigned to any task 
		if (!employeeTaskQuery.list().isEmpty())
			return false;

		Query employeeTimesheetQuery = currentSession().createQuery("from Timesheet ts where ts.who.id = :id");
		employeeTimesheetQuery.setParameter("id", employee.getId());
		//employee mustn't be assigned to any timesheet
		if (!employeeTimesheetQuery.list().isEmpty())
			return false;
		
		delete(employee);
		return true;
	}
	
	@Override
	public List<Employee> getUnassigedEmployee() {
		Query employeeTimesheetQuery = currentSession().createQuery("from Employee e where e.id not in elements()");
		
		return employeeTimesheetQuery.list();
	}

}
