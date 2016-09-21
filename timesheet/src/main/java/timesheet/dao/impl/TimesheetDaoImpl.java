package timesheet.dao.impl;

import java.util.List;

import org.hibernate.Criteria;

import timesheet.dao.TimesheetDao;
import timesheet.domain.Timesheet;

public class TimesheetDaoImpl extends HibernateDao<Timesheet, Long> implements TimesheetDao {
	 
    @Override
    public List<Timesheet> list() {
        return currentSession().createCriteria(Timesheet.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}

