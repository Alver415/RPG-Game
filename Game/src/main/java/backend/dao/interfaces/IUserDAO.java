package backend.dao.interfaces;

public interface IUserDAO {

	boolean checkUserExists(String username);

	boolean checkPassword(String username, String password);

}
