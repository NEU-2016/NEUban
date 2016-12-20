package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.web.handlers.GlobalControllerExceptionHandler;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public abstract class AbstractControllerTest {

	protected static final String VIEW_PREFIX = "/templates/";
	protected static final String VIEW_SUFFIX = ".html";

	protected static final String ERROR_VIEW = "error";
	protected static final String ERROR_MESSAGE_MODEL_OBJECT_NAME = "errorMessage";

	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(
				this.controllerUnderTest()
		).setControllerAdvice(
				new GlobalControllerExceptionHandler()
		).setViewResolvers(
				viewResolver()
		).build();
	}

	protected abstract Object[] controllerUnderTest();

	protected ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setPrefix(VIEW_PREFIX);
		viewResolver.setSuffix(VIEW_SUFFIX);

		return viewResolver;
	}
}
