package backend.dao.interfaces;

import backend.HibernateObject;

public interface IBaseDAO {

	<T extends HibernateObject> T load(Class<T> clazz, Integer id);

	void save(HibernateObject object);

}
