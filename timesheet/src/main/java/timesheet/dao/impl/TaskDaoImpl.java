package timesheet.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import timesheet.dao.TaskDao;
import timesheet.domain.Task;

@Repository("taskDao")
public class TaskDaoImpl extends HibernateDao<Task, Long> implements TaskDao {

	@Override
	public boolean removeTask(Task task) {
		Query taskQuery = currentSession().createQuery("from Timesheet t where t.task.id = :id");
		taskQuery.setParameter("id", task.getId());

		// task mustn't be assigned to no timesheet
		if (!taskQuery.list().isEmpty()) {
			return false;
		}

		delete(task);
		return true;
	}

	@Override
	public List<Task> list() {
		return currentSession().createCriteria(Task.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}

}
