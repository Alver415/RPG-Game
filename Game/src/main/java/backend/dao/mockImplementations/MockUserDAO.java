package backend.dao.mockImplementations;

import backend.dao.implementations.UserDAO;

public class MockUserDAO extends UserDAO {

	@Override
	public boolean checkPassword(String username, String password) {
		return true;
	}

	@Override
	public boolean checkUserExists(String username) {
		return true;
	}
}