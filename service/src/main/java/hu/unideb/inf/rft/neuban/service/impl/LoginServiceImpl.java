package hu.unideb.inf.rft.neuban.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.LoginService;
import hu.unideb.inf.rft.service.dto.UserDto;

/**
 * Service for login
 * 
 * @author Headswitcher
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * @return Return true if the username and password matches , else returns false 
	 *
	 */
	@Override
	public Boolean isItValidLogin(String userName, String password) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		Type listType = new TypeToken<List<UserDto>>() {}.getType();
		List<UserDto> allUser = modelMapper.map(userRepository.findAll(), listType);
		for (UserDto userDto : allUser) {
			if (userDto.getUserName().equalsIgnoreCase(userName)){
				if (userDto.getPassword().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}

}
