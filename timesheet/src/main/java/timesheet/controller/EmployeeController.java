package timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import timesheet.controller.exception.EmployeeDeleteException;
import timesheet.dao.EmployeeDao;
import timesheet.domain.Employee;
/**
 * Controller for handling Employees.
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController extends AbstractController {

	@Autowired
	private EmployeeDao employeeDao;
	
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/**
     * Retrieves employees, puts them in the model and returns corresponding view
     * @param model Model to put employees to
     * @return employees/list
     */
	@RequestMapping(method = RequestMethod.GET)
	public String listEmployees(Model model) {
		model.addAttribute("employees", employeeDao.list());
		return "employees/list";
	}
	
	/**
     * Returns employee with specified ID
     * @param id Employee's ID
     * @param model Model to put employee to
     * @return employees/view
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getEmployee(Model model, @PathVariable("id") long id) {
		model.addAttribute("employee", employeeDao.find(id));
		return "employees/view";
	}
	
	/**
     * Updates employee with specified ID
     * @param id Employee's ID
     * @param employee Employee to update (bounded from HTML form)
     * @return redirects to employees
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String updateEmployee(Model model, @PathVariable("id") long id, Employee employee) {
		employee.setId(id);
		employeeDao.update(employee);
		model.addAttribute("employee", employee);
		return "employees/view";
	}
	
	/**
     * Saves new employee to the database
     * @param employee Employee to save
     * @return redirects to employees
     */
	@RequestMapping(method = RequestMethod.POST)
	public String addEmployee(Employee employee) {
		employeeDao.insert(employee);
		return "redirect:/employees";
	}
	
	/**
     * Creates form for new employee
     * @param model Model to bind to HTML form
     * @return employees/new
     */
	@RequestMapping(params = "new", method = RequestMethod.GET)
	public String createEmployeeForm(Model model) {
	    model.addAttribute("employee", new Employee());
	    return "employees/new";
	}
	
	/**
	 * Deletes employee with specified ID
	 * @param id Employee's ID
	 * @return redirects to employees if everything was ok
	 * @throws EmployeeDeleteException When employee cannot be deleted
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteEmployee(@PathVariable("id") long id)
	        throws EmployeeDeleteException {
	 
	    Employee toDelete = employeeDao.find(id);
	    boolean wasDeleted = employeeDao.removeEmployee(toDelete);
	 
	    if (!wasDeleted) {
	        throw new EmployeeDeleteException(toDelete);
	    }
	 
	    // everything OK, see remaining employees
	    return "redirect:/employees";
	}
	
	@RequestMapping(value = "/employeesRest", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Employee getAllEmployees()
    {
        List<Employee> employees = employeeDao.list();
        return employees.get(0);
    }
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public void getError() throws EmployeeDeleteException
    {
        throw new EmployeeDeleteException(new Employee());
    }
	
	/**
	 * Handles EmployeeDeleteException
	 * @param e Thrown exception with employee that couldn't be deleted
	 * @return binds employee to model and returns employees/delete-error
	 */
	@ExceptionHandler(EmployeeDeleteException.class)
	public ModelAndView handleDeleteException(EmployeeDeleteException e) {
	    ModelMap model = new ModelMap();
	    model.put("employee", e.getEmployee());
	    return new ModelAndView("employees/delete-error", model);
	}
	
	
}
