package timetakers.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by david on 30.11.2016.
 */

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getErrorPageAsHtml() {
        return new ModelAndView("error");
    }

}
