package hu.unideb.inf.rft.neuban.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundException;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;

/**
 * Validator for login
 * 
 * @author Headswitcher
 *
 */
@Service
public class UserValidator {

	@Autowired
	private UserService userService;

	/**
	 * @return Return true if the username and password matches , else returns
	 *         false
	 *
	 */
	public boolean isValidLogin(String userName, String password) throws UserNotFoundException {

		Assert.notNull(userName);
		Assert.notNull(password);

		UserDto userDto = userService.getByUserName(userName);

		if (userDto == null) {
			throw new UserNotFoundException();
		}
		return userDto.getPassword().equals(password);
	}

}
