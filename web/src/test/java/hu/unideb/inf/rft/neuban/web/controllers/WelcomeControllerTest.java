package hu.unideb.inf.rft.neuban.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class WelcomeControllerTest extends AbstractControllerTest {

    private static final String REQUEST_URL = "/secure/welcome";
    private static final String VIEW_NAME = "secure/welcome";

    @InjectMocks
    private WelcomeController welcomeController;

    @Override
    protected Object[] controllerUnderTest() {
        return new Object[]{this.welcomeController};
    }

    @Test
    public void loadWelcomeViewShouldRenderWelcomeView() throws Exception {
        this.mockMvc.perform(get(REQUEST_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME))
                .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
    }

}
