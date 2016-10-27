package timesheet.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import timesheet.controller.exception.TaskDeleteException;
import timesheet.dao.EmployeeDao;
import timesheet.dao.ManagerDao;
import timesheet.dao.TaskDao;
import timesheet.domain.Employee;
import timesheet.domain.Task;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController extends AbstractController{

	private ManagerDao managerDao;
	private EmployeeDao employeeDao;
	private TaskDao taskDao;
	
	public ManagerDao getManagerDao() {
		return managerDao;
	}
	@Autowired
	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}
	@Autowired
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	public TaskDao getTaskDao() {
		return taskDao;
	}
	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	/**
	 * Retrieves tasks, puts them in the model and returns corresponding view
	 * @param model Model to put tasks to
	 * @return tasks/list
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String listTasks(Model model) {
	    model.addAttribute("tasks", taskDao.list());
	 
	    return "tasks/list";
	}
	
	/**
	 * Deletes task with specified ID
	 * @param id Task's ID
	 * @return redirects to tasks if everything was ok
	 * @throws TaskDeleteException When task cannot be deleted
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteTask(@PathVariable("id") long id) 
	        throws TaskDeleteException {
	 
	    Task toDelete = taskDao.find(id);
	    boolean wasDeleted = taskDao.removeTask(toDelete);
	 
	    if (!wasDeleted) {
	        throw new TaskDeleteException(toDelete);
	    }
	 
	    // everything OK, see remaining tasks
	    return "redirect:/tasks";
	}
	
	@ExceptionHandler(TaskDeleteException.class)
	public ModelAndView handleDeleteException(TaskDeleteException e) {
		ModelMap model = new ModelMap();
	    model.put("task", e.getTask());
	    return new ModelAndView("tasks/delete-error", model);
	}
	
	/**
	 * Returns task with specified ID
	 * @param id Tasks's ID
	 * @param model Model to put task to
	 * @return tasks/view
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getTask(@PathVariable("id") long id, Model model) {
	    Task task = taskDao.find(id);
	    model.addAttribute("task", task);
	 
	    // add all remaining employees
	    List<Employee> employees = employeeDao.list();
	    Set<Employee> unassignedEmployees = new HashSet<Employee>();
	 
	    for (Employee employee : employees) {
	        if (!task.getAssignedEmployees().contains(employee)) {
	            unassignedEmployees.add(employee);
	        }
	    }
	 
	    model.addAttribute("unassigned", unassignedEmployees);
	 
	    return "tasks/view";
	}
}
