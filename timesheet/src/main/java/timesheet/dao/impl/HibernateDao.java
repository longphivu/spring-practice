package timesheet.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import timesheet.dao.GenericDao;

@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
public class HibernateDao<E, K extends Serializable> implements GenericDao<E, K> {

	private SessionFactory sessionFactory;
    protected Class<? extends E> daoType;
	
    public HibernateDao() {
		// Reflection is use here because java doesn't have generic at runtime,
		// only at compile time. So we can't use something like E.class 
        daoType = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

	// We use getter injection here to make it easier to write unit test. If we
	// use field injection, we cannot set other dependencies for private fields.
	// And if we use constructor injection, every other subclass
	// would have to have constructor matching the one from super class
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    /*Basic CRUD operation*/
  
	@Override
	public void insert(E entity) {
		currentSession().persist(entity);
	}

	@Override
	public void update(E entity) {
		currentSession().update(entity);
	}

	@Override
	public void delete(E entity) {
		currentSession().delete(entity);
	}

	@Override
	public E find(K key) {
		return (E) currentSession().get(daoType, key);
	}

	@Override
	public List<E> list() {
		return currentSession().createCriteria(daoType).list();
	}

}
