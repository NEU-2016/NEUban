package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

    private static final String REGISTER_VIEW = "register";
    private static final String INDEX_VIEW = "index";

    @Autowired
    UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String loadRegisterView() {
        return REGISTER_VIEW;
    }

    @RequestMapping(path = "/register/register", method = RequestMethod.GET)
    public String register() {
        return INDEX_VIEW;
    }
}
