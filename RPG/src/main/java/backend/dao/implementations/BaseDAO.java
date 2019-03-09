package backend.dao.implementations;

import org.hibernate.Session;

import backend.HibernateObject;
import backend.HibernateUtil;
import backend.dao.interfaces.IBaseDAO;

public class BaseDAO implements IBaseDAO {

	public void save(HibernateObject obj) {
		Session session = getSession();
		session.beginTransaction();
		session.save(obj);
		session.getTransaction().commit();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends HibernateObject> T load(Class<T> clazz, Integer id) {
		Session session = getSession();
		session.beginTransaction();
		T obj = (T) session.load(clazz, id);
		session.getTransaction().commit();
		return obj;
	}

	private Session getSession() {
		return HibernateUtil.getSession();
	}

}