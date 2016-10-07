package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.inf.rft.neuban.service.ExampleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ExampleController {
	private static final String DEFAULT_PATH_MESSAGE = "You are here with default path(/): localhost:8080/";
	private static final String EXAMPLE_PATH_MESSAGE = "You are here with exmaple path(/example): locahost:8080/example";

	@Autowired
	private ExampleService exampleService;
	
	@RequestMapping(path = "/")
	public String defaultPath() {
		log.info(ExampleController.class.getName() + "\nExample service is not null: " + (this.exampleService != null));
		return DEFAULT_PATH_MESSAGE;
	}
	
	@RequestMapping(path = "/example")
	public String exampleView() {
		log.info(ExampleController.class.getName() + "\nExample service is not null: " + (this.exampleService != null));
		return EXAMPLE_PATH_MESSAGE;
	}
}
