package gui;

import backend.HibernateObject;
import backend.dao.interfaces.IBaseDAO;
import backend.dao.interfaces.IUserDAO;
import backend.dao.mock.MockBaseDAO;
import backend.dao.mock.MockUserDAO;
import gui.components.Message;
import gui.components.Message.Type;
import gui.components.MessagePane;

public class Facade {

	private static GameApplication	APPLICATION;
	private static final IBaseDAO	BASE_DAO	= new MockBaseDAO();	// new BaseDAO();
	private static final IUserDAO	USER_DAO	= new MockUserDAO();	// new UserDAO();

	public static void save(HibernateObject object) {
		BASE_DAO.save(object);
	}

	public static <T extends HibernateObject> T load(Class<T> clazz, Integer id) {
		return BASE_DAO.load(clazz, id);
	}

	public static boolean checkUserExists(String username) {
		return USER_DAO.checkUserExists(username);
	}

	public static boolean checkPassword(String username, String password) {
		return USER_DAO.checkPassword(username, password);
	}

	public static void addMessage(String message) {
		addMessage(null, message);
	}

	public static void addMessage(String title, String message) {
		addMessage(null, message, Type.NORMAL);
	}

	public static void addMessage(String title, String message, Type type) {
		((MessagePane) APPLICATION.getStage().getScene().lookup(MessagePane.ID)).addMessage(title, message, type);
	}

	public static void removeMessage(Message message) {
		((MessagePane) APPLICATION.getStage().getScene().lookup(MessagePane.ID)).removeMessage(message);
	}

	public static GameApplication getApplication() {
		return APPLICATION;
	}

	public static void setApplication(GameApplication application) {
		APPLICATION = application;
	}

}
