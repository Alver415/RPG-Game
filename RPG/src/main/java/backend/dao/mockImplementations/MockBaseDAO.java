package backend.dao.mockImplementations;

import backend.HibernateObject;
import backend.dao.implementations.BaseDAO;

public class MockBaseDAO extends BaseDAO {

	@Override
	public void save(HibernateObject obj) {
		return;
	}

	@Override
	public <T extends HibernateObject> T load(Class<T> clazz, Integer id) {
		return null;
	}

}