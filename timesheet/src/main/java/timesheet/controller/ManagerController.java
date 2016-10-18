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

import timesheet.controller.exception.ManagerDeleteException;
import timesheet.dao.ManagerDao;
import timesheet.domain.Employee;
import timesheet.domain.Manager;

@Controller
@RequestMapping("/managers")
public class ManagerController extends AbstractController {

	private ManagerDao managerDao;
	 
    @Autowired
    public void setManagerDao(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }
 
    public ManagerDao getManagerDao() {
        return managerDao;
    }
    
    /**
     * Returns list of all managers
     * @param model Model to put manager to
     * @return managers/list
     */
    @RequestMapping(method = RequestMethod.GET)
    public String listManagers(Model model) {
    	model.addAttribute("managers", managerDao.list());
    	return "managers/list";
    }
    
    /**
     * Returns manager with specified ID
     * @param id Managers's ID
     * @param model Model to put manager to
     * @return managers/view
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getManager(Model model, @PathVariable("id") long id) {
    	model.addAttribute("manager", managerDao.find(id));
    	return "managers/view";
    }
    
    /**
     * Returns page to add new manager
     * @param model Model to put default manager to
     * @return managers/new
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String createManager(Model model) {
    	model.addAttribute("manager", new Employee());
	    return "managers/new";
    }
    
    /**
     * Returns redirect to list of managers after insertion
     * @param model Model to put manager to
     * @return inserts manager and returns redirect:/managers
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addManager(Manager manager) {
    	managerDao.insert(manager);
    	return "redirect:/managers";
    }
    
    /**
     * Returns redirect to list of managers after deletion
     * @param id Managers's ID
     * @return deletes manager and returns redirect:/managers
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteManager(@PathVariable("id") long id) throws ManagerDeleteException {
    	Manager toDelete = managerDao.find(id);
        boolean wasDeleted = managerDao.removeManager(toDelete);
     
        if (!wasDeleted) {
            throw new ManagerDeleteException(toDelete);
        }
        
    	return "redirect:/managers";
    }
    
    /**
     * Handles ManagerDeleteException
     * @param e Thrown exception with manager that couldn't be deleted
     * @return binds manager to model and returns managers/delete-error
     */
    @ExceptionHandler(ManagerDeleteException.class)
    public ModelAndView handleDeleteException(ManagerDeleteException e) {
        ModelMap model = new ModelMap();
        model.put("manager", e.getManager());
        return new ModelAndView("managers/delete-error", model);
    }
}
