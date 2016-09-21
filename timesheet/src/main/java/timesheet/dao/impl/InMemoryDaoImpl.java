package timesheet.dao.impl;

import java.util.ArrayList;
import java.util.List;

import timesheet.dao.GenericDao;

public class InMemoryDaoImpl<E, K> implements GenericDao<E, K>{

	private List<E> entities = new ArrayList<E>();
	 
    public void insert(E entity) {
        entities.add(entity);
    }
 
    public void update(E entity) {
        throw new UnsupportedOperationException("Not supported in dummy in-memory impl!");
    }
 
    public void delete(E entity) {
        entities.remove(entity);
    }
 
    public E find(K key) {
        if (entities.isEmpty()) {
            return null;
        }
        // just return the first one since we are not using any keys ATM
        return entities.get(0);
    }
 
    public List<E> list() {
        return entities;
    }


}
