package hu.unideb.inf.rft.neuban.web.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/login", "/register", "/index").permitAll()
				.antMatchers("/secure/**").authenticated()
				.and().formLogin().loginPage("/login").loginProcessingUrl("/appLogin")
				.usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/secure/welcome", true).failureUrl("/login-error")
				.and().logout().logoutUrl("/appLogout").logoutSuccessUrl("/login").invalidateHttpSession(true);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//TODO use password encoder when encoding is applied to new users too
		//auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
