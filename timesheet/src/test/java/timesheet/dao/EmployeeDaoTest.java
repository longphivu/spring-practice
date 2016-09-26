package timesheet.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import timesheet.DomainAwareBase;
import timesheet.domain.Employee;
import timesheet.domain.Manager;
import timesheet.domain.Task;
import timesheet.domain.Timesheet;

public class EmployeeDaoTest extends DomainAwareBase{
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	TaskDao taskDao;
	
	@Autowired
	ManagerDao managerDao;
	
	@Autowired
	TimesheetDao timesheetDao;
	
	@Test
	public void testAdd() {
		Employee e = new Employee("test-employee", "IT");
		
		int size = employeeDao.list().size();
		employeeDao.insert(e);
		
		assertNotNull(employeeDao.find(e.getId())); //employee id should be populated
		assertTrue(size < employeeDao.list().size()); //new size must greater than old size
	}
	
	@Test
	public void testUpdate() {
		Employee e = new Employee("test-employee", "BA");
		employeeDao.insert(e);
		
		e.setName("test-updated");
		employeeDao.update(e);
		Employee found = employeeDao.find(e.getId());
		
		assertEquals("test-updated", found.getName());
	}
	
	@Test
	public void testDelete() {
		Employee e = new Employee("test-employee", "IT");
		employeeDao.insert(e);
		
		employeeDao.delete(e);
		e = employeeDao.find(e.getId());
		
		assertNull(e);
	}
	
	@Test
	public void testList() {
		int size = employeeDao.list().size();
		
		List<Employee> employees = Arrays.asList(
                new Employee("test-1", "testers"),
                new Employee("test-2", "testers"),
                new Employee("test-3", "testers"));
        for (Employee employee : employees) {
            employeeDao.insert(employee);
        }

        assertEquals(size + 3, employeeDao.list().size());
	}
	
	@Test
    public void testRemoveEmployee() {
        Manager manager = new Manager("task-manager");
        managerDao.insert(manager);
 
        Employee employee = new Employee("Jaromir", "Hockey");
        employeeDao.insert(employee);
 
        Task task = new Task("test-task", manager, employee);
        taskDao.insert(task);
 
        Timesheet timesheet = new Timesheet(employee, task, 100);
        timesheetDao.insert(timesheet);
 
        // try to remove -> shouldn't work
        assertFalse(employeeDao.removeEmployee(employee));
 
        // remove stuff
        timesheetDao.delete(timesheet);
        taskDao.delete(task);
 
        // should work -> employee is now free
        assertTrue(employeeDao.removeEmployee(employee));
    }

}
