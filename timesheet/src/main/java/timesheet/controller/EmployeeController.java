package timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import timesheet.dao.EmployeeDao;

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
	
	@RequestMapping("/{id}")
	public String getEmployee(Model model, @PathVariable("id") long id) {
		model.addAttribute("employee", employeeDao.find(id));
		return "employees/view";
	}
	
}
