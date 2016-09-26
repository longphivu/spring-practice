package timesheet.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import timesheet.DomainAwareBase;
import timesheet.domain.Employee;
import timesheet.domain.Manager;
import timesheet.domain.Task;

public class TaskDaoTest extends DomainAwareBase{
	
	@Autowired
	TaskDao taskDao;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	ManagerDao managerDao;
	
	@Autowired
	TimesheetDao timesheetDao;
	
	@Test
	public void testAdd() {
		Task task = newTask();
		int size = taskDao.list().size();
		taskDao.insert(task);
		
		assertTrue(size < taskDao.list().size());
	}
	
	@Test
	public void testUpdate() {
		Task task = newTask();
		
		taskDao.insert(task);
		task.setDescription("new Description");
		taskDao.update(task);
		Task task2 = taskDao.find(task.getId());
		
		assertEquals("new Description", task2.getDescription());
	}
	
	
	/**
     * @return Dummy task for testing
     */
    private Task newTask() {
        Manager bob = new Manager("Bob");
        managerDao.insert(bob);
 
        Employee steve = new Employee("Steve", "Business");
        Employee woz = new Employee("Woz", "Engineering");
        employeeDao.insert(steve);
        employeeDao.insert(woz);
 
        return new Task("Learn Spring", bob, steve, woz);
    }

}
