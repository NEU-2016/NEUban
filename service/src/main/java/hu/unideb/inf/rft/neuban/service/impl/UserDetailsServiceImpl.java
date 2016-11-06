package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Sets;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUsernameException;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username);
        UserDto userDto = this.userService
                .getByUserName(username)
                .orElseThrow(() -> new NonExistentUsernameException(username));

        Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet(new SimpleGrantedAuthority(userDto.getRole().name()));
        return new User(userDto.getUserName(), userDto.getPassword(), grantedAuthorities);
    }
}
