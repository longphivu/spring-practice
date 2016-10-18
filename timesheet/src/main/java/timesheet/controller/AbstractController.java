package timesheet.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractController {
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e) {
	    ModelMap model = new ModelMap();
	    model.addAttribute("track", e.getMessage());
	    return new ModelAndView("error", model);
	}
}
