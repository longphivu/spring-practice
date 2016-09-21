package timesheet.dao;

import java.util.List;

public interface GenericDao<E, K> {
	
	void insert(E entity);
	
	void update(E entity);
	
	void delete(E entity);
	
	E find(K key);
	
	List<E> list();
}
