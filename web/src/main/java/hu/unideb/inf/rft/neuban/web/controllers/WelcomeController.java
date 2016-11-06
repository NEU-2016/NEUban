package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

    private static final String WELCOME_VIEW = "secure/welcome";

    @RequestMapping(path = "/secure/welcome", method = RequestMethod.GET)
    public String loadWelcomeView() {
        return WELCOME_VIEW;
    }
}
