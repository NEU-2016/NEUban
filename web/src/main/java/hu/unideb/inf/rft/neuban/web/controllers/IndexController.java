package hu.unideb.inf.rft.neuban.web.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class IndexController {

    private static final String INDEX_VIEW = "index";

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String loadIndexView() {
        return INDEX_VIEW;
    }
}
