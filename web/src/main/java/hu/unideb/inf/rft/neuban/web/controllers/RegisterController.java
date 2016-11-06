package hu.unideb.inf.rft.neuban.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

    private static final String REGISTER_VIEW = "register";

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String loadRegisterView() {
        return REGISTER_VIEW;
    }
}
