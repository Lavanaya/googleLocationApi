package com.company;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by lavanyadixit on 23/04/17.
 */
@RestController
@Controller
public class ControllerClass {

   // @JsonView(Views.Public.class)


    @RequestMapping(value = "/setLocation", method = RequestMethod.POST)
    public String printWelcome(@RequestBody String location) {
        System.out.println(location);
        return "hello";

    }

}
