package timesheet.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import timesheet.DomainAwareBase;
import timesheet.dao.EmployeeDao;
import timesheet.dao.ManagerDao;
import timesheet.domain.Employee;
import timesheet.domain.Manager;
import timesheet.domain.Task;

public class TimesheetServiceTest extends DomainAwareBase{
	
	@Autowired
	TimesheetService timesheetService;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	ManagerDao	managerDao;
	
	@Test
    public void testBusiestTask() {
        Task task = timesheetService.busiestTask();
        assertTrue(1 == task.getId());
    }
     
    @Test
    public void testTasksForEmployees() {
        Employee steve = employeeDao.find(1L);
        Employee bill = employeeDao.find(2L);
         
        assertEquals(2, timesheetService.tasksForEmployee(steve).size());
        assertEquals(1, timesheetService.tasksForEmployee(bill).size());
    }
     
    @Test
    public void testTasksForManagers() {
        Manager eric = managerDao.find(1L);
        assertEquals(1, timesheetService.tasksForManager(eric).size());
    }


}
