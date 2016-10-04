package timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import timesheet.controller.exception.EmployeeDeleteException;
import timesheet.dao.EmployeeDao;
import timesheet.domain.Employee;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeDao employeeDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String listEmployees(Model model) {
		model.addAttribute("employees", employeeDao.list());
		return "employees/list";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getEmployee(Model model, @PathVariable("id") long id) {
		model.addAttribute("employee", employeeDao.find(id));
		return "employees/view";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String updateEmployee(Model model, @PathVariable("id") long id, Employee employee) {
		employeeDao.update(employee);
		model.addAttribute("employee", employee);
		return "employees/view";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public String addEmployee(Model model, Employee employee) {
		employeeDao.insert(employee);
		model.addAttribute("employee", employee);
		return "redirect:/employees";
	}
	
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
