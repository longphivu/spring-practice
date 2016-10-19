package timesheet.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import timesheet.DomainAwareBase;
import timesheet.controller.exception.ManagerDeleteException;
import timesheet.dao.ManagerDao;
import timesheet.domain.Manager;

public class ManagerControllerTest extends DomainAwareBase {

	@Autowired
	private ManagerDao managerDao;

	@Autowired
	private ManagerController controller;

	private Model model; // used for controller

	@Before
    public void setUp() {
        model = new ExtendedModelMap();
        deleteAllDomainEntites();
    }
 
    @After
    public void cleanUp() {
    	deleteAllDomainEntites();
    }

	@Test
	public void testShowManagers() {
		// prepare some data
		Manager manager = new Manager("Bob Dylan");
		managerDao.insert(manager);

		// use controller
		String view = controller.listManagers(model);
		assertEquals("managers/list", view);

		List<Manager> listFromDao = managerDao.list();
		Collection<?> listFromModel = (Collection<?>) model.asMap().get("managers");

		assertTrue(listFromModel.contains(manager));
		assertTrue(listFromDao.containsAll(listFromModel));
	}

	@Test
	public void testDeleteManagerOk() throws ManagerDeleteException {
		// prepare ID to delete
		Manager john = new Manager("John Lennon");
		managerDao.insert(john);
		long id = john.getId();

		// delete & assert
		String view = controller.deleteManager(id);
		assertEquals("redirect:/managers", view);
		assertNull(managerDao.find(id));
	}

	@Test(expected = ManagerDeleteException.class)
	public void testDeleteManagerThrowsException() throws ManagerDeleteException {
		// prepare ID to delete
		Manager john = new Manager("John Lennon");
		managerDao.insert(john);
		long id = john.getId();

		// mock DAO for this call
		ManagerDao mockedDao = mock(ManagerDao.class);
		when(mockedDao.removeManager(john)).thenReturn(false);

		ManagerDao originalDao = controller.getManagerDao();
		try {
			// delete & expect exception
			controller.setManagerDao(mockedDao);
			controller.deleteManager(id);
		} finally {
			controller.setManagerDao(originalDao);
		}
	}

	@Test
	public void testHandleDeleteException() {
		Manager john = new Manager("John Lennon");
		ManagerDeleteException e = new ManagerDeleteException(john);
		ModelAndView modelAndView = controller.handleDeleteException(e);

		assertEquals("managers/delete-error", modelAndView.getViewName());
		assertTrue(modelAndView.getModelMap().containsValue(john));
	}

	@Test
	public void testGetManager() {
		// prepare manager
		Manager george = new Manager("George Harrison");
		managerDao.insert(george);
		long id = george.getId();

		// get & assert
		String view = controller.getManager(model, id);
		assertEquals("managers/view", view);
		assertEquals(george, model.asMap().get("manager"));
	}

	@Test
	public void testUpdateManager() {
		// prepare manager
		Manager ringo = new Manager("Ringo Starr");
		managerDao.insert(ringo);
		long id = ringo.getId();

		// user alters manager in HTML form
		ringo.setName("Rango Starr");

		// update & assert
		String view = controller.updateManager(model, id, ringo);
		assertEquals("/managers/view", view);
		assertEquals("Rango Starr", managerDao.find(id).getName());
	}

	@Test
	public void testinsertManager() {
		// prepare manager
		Manager paul = new Manager("Paul McCartney");

		// save but via controller
		String view = controller.addManager(paul);
		assertEquals("redirect:/managers", view);

		// manager is stored in DB
		assertEquals(paul, managerDao.find(paul.getId()));
	}
}