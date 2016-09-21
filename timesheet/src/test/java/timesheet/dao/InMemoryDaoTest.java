package timesheet.dao;

import static org.junit.Assert.*;

import java.util.List;
 
import org.junit.Before;
import org.junit.Test;
import timesheet.dao.impl.InMemoryDaoImpl;
import timesheet.domain.Employee;

public class InMemoryDaoTest {
	private GenericDao<Employee, Long> employeeDao = new InMemoryDaoImpl<Employee, Long>();
	
	@Before
	public void setup(){
		for (int i = 0; i < 5; i++) {
            Employee e = new Employee("Mike " + i, "IT");
            employeeDao.insert(e);
        }
	}
	
	@Test
    public void testAdd() {
        int oldSize = employeeDao.list().size();
        Employee e = new Employee("Bob", "IT");
        employeeDao.insert(e);
        int newSize = employeeDao.list().size();
         
        assertFalse (oldSize == newSize);
    }
     
    @Test
    public void testRemove() {
        int oldSize = employeeDao.list().size();
        Employee e = employeeDao.find(1L);
        employeeDao.delete(e);
        int newSize = employeeDao.list().size();
         
        assertFalse (oldSize == newSize);
    }
     
    @Test
    public void testUpdate() {
        //TODO: need real implementation
    }
     
    @Test
    public void testList() {
        List<Employee> list = employeeDao.list();
        assertNotNull (list);
        assertFalse (list.isEmpty());
    }

}
