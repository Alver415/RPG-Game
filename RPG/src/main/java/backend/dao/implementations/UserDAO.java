package backend.dao.implementations;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import backend.HibernateUtil;
import backend.PasswordAuthentication;
import backend.dao.interfaces.IUserDAO;

public class UserDAO implements IUserDAO {

	private static final String CHECK_PASSWORD_QUERY = "SELECT user.password FROM User user WHERE user.username = :username";

	public boolean checkPassword(String username, String password) {
		Session session = getSession();
		Query query = session.createQuery(CHECK_PASSWORD_QUERY);
		query.setParameter("username", username);

		List<?> results = query.list();
		if (results.isEmpty()) {
			return false;
		} else {
			String token = (String) results.get(0);
			return new PasswordAuthentication().authenticate(password.toCharArray(), token);
		}

	}

	private static final String USER_EXISTS_QUERY = "FROM User user WHERE user.username = :username";

	public boolean checkUserExists(String username) {
		Session session = getSession();
		Query query = session.createQuery(USER_EXISTS_QUERY);
		query.setParameter("username", username);

		List<?> results = query.list();
		return !results.isEmpty();
	}

	private Session getSession() {
		return HibernateUtil.getSession();
	}
}