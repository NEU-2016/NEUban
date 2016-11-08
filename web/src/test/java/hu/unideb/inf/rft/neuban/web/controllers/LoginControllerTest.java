package hu.unideb.inf.rft.neuban.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.stereotype.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest extends AbstractControllerTest {

    private static final String REQUEST_URL = "/login";
    private static final String VIEW_NAME = "login";

    @InjectMocks
    private LoginController loginController;

    @Override
    protected Object[] controllerUnderTest() {
        return new Object[]{this.loginController};
    }

    @Test
    public void loadLoginViewShouldRenderLoginView() throws Exception {
        this.mockMvc.perform(get(REQUEST_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME))
                .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
    }
}
