package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.Principal;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class WelcomeControllerTest extends AbstractControllerTest {

	private static final String REQUEST_URL = "/secure/welcome";
	private static final String VIEW_NAME = "secure/welcome";

	@InjectMocks
	private WelcomeController welcomeController;

	@Mock
	private Principal principal;

	@Mock
	private UserService userService;

	@Mock
	private BoardService boardService;

	@Override
	protected Object[] controllerUnderTest() {
		return new Object[]{this.welcomeController};
	}

	@Test
	public void loadWelcomeViewShouldRenderWelcomeView() throws Exception {
		when(principal.getName()).thenReturn("test");
		when(userService.getByUserName(anyString())).thenReturn(Optional.of(UserDto.builder().id(1L).build()));
		when(boardService.getAllByUserId(anyLong())).thenReturn(null);
		this.mockMvc.perform(get(REQUEST_URL).principal(principal))
				.andExpect(status().isOk())
				.andExpect(view().name(VIEW_NAME))
				.andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
	}

}
