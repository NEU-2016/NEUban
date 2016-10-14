package hu.unideb.inf.rft.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import hu.unideb.inf.rft.neuban.service.UserService;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundExepction;
import hu.unideb.inf.rft.service.dto.UserDto;

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
	public boolean isValidLogin(String userName, String password) throws Exception {

		Assert.notNull(userName);
		Assert.notNull(password);

		UserDto userDto = userService.getByUserName(userName);

		if (userDto == null) {
			throw new UserNotFoundExepction();
		}
		return userDto.getPassword().equals(password);
	}

}
