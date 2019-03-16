package project.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ErrorPageController implements ErrorController {
    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public ModelAndView getErrorPage(){
        ModelAndView modelAndView = new ModelAndView("RedirectPage");
        modelAndView.addObject("redirectUrl", "/home");
        modelAndView.addObject("redirectMessage", "An error has occurred.\r \n You will be redirected to home page.");
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
