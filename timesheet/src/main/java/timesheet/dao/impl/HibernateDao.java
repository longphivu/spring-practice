package timesheet.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import timesheet.dao.GenericDao;

public class HibernateDao<E, K extends Serializable> implements GenericDao<E, K> {

	private SessionFactory sessionFactory;
    protected Class<? extends E> daoType;
	
	@Override
	public void insert(E entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(E entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(E entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E find(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
