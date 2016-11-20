package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/register")
public class RegisterController {

    private static final String REGISTER_VIEW = "register";
    private static final String INDEX_VIEW = "index";

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userValidator")
    private Validator userValidator;

    @GetMapping
    public ModelAndView loadRegisterView() {
        ModelAndView modelAndView = new ModelAndView(REGISTER_VIEW);
        modelAndView.addObject("userDto", new UserDto());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView userRegister(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult) {
        this.userValidator.validate(userDto, bindingResult);
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(REGISTER_VIEW);
            addErrorsToModelAndView(modelAndView, bindingResult.getAllErrors());
        } else {
            modelAndView.setViewName(INDEX_VIEW);
            userService.saveOrUpdate(userDto);
        }
        return modelAndView;
    }

    //TODO Pull out into some sort of common method collection class
    private void addErrorsToModelAndView(ModelAndView modelAndView, List<ObjectError> errors) {
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                modelAndView.addObject(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
    }
}
