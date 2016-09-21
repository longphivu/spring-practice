package timesheet.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import timesheet.dao.ManagerDao;
import timesheet.domain.Manager;

@Repository("managerDao")
public class ManagerDaoImpl extends HibernateDao<Manager, Long> implements ManagerDao {

	@Override
	public boolean removeManager(Manager manager) {
		Query managerTaskQuery = currentSession().createQuery("from Task t where t.manager.id = :id");
		managerTaskQuery.setParameter("id", manager.getId());
		// manager mustn't be assigned to any task
		if (!managerTaskQuery.list().isEmpty())
			return false;
		
		delete(manager);
		return true;
	}

}
