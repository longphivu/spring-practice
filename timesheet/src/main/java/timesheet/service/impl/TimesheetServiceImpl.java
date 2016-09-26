package timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import timesheet.dao.TaskDao;
import timesheet.dao.TimesheetDao;
import timesheet.domain.Employee;
import timesheet.domain.Manager;
import timesheet.domain.Task;
import timesheet.service.TimesheetService;

@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
@Service("timesheetService")
public class TimesheetServiceImpl implements TimesheetService{
	
	//dependencies
	SessionFactory sessionFactory;
	TimesheetDao timesheetDao;
	TaskDao taskDao;

	@Override
	public Task busiestTask() {
		List<Task> tasks = taskDao.list();
		
		if (tasks.size() == 0) return null;
		
		Task busiestTask = tasks.get(0);
		for (Task task : tasks) {
			if (task.getAssignedEmployees().size() > busiestTask.getAssignedEmployees().size()) {
				busiestTask = task;
			}
		}
		
		return busiestTask;
	}

	@Override
	public List<Task> tasksForEmployee(Employee e) {
		List<Task> allTasks = taskDao.list();
        List<Task> tasksForEmployee = new ArrayList<Task>();
         
        for (Task task : allTasks) {
            if (task.getAssignedEmployees().contains(e)) {
                tasksForEmployee.add(task);
            }
        }
 
        return tasksForEmployee;
	}

	@Override
    public List<Task> tasksForManager(Manager manager) {
		//for demo purpose only, any thing relate to query should be moved to DAO layer
        Query query = currentSession()
                .createQuery("from Task t where t.manager.id = :id");
        query.setParameter("id", manager.getId());
        return query.list();
    }

	private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

	/*Getters & Setter*/
	
	public SessionFactory getSectionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSectionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public TimesheetDao getTimesheetDao() {
		return timesheetDao;
	}

	@Autowired
	public void setTimesheetDao(TimesheetDao timesheetDao) {
		this.timesheetDao = timesheetDao;
	}

	public TaskDao getTaskDao() {
		return taskDao;
	}

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

}
