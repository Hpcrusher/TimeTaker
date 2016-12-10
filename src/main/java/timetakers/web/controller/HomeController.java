package timetakers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import timetakers.repository.PersonRepository;
import timetakers.web.assembler.PersonAssembler;

/**
 * @author David Liebl
 */

@Controller
@RequestMapping(value = "/home")
public class HomeController {

    private final PersonRepository personRepository;
    private final PersonAssembler personAssembler;

    @Autowired
    public HomeController(PersonRepository personRepository, PersonAssembler personAssembler) {
        this.personRepository = personRepository;
        this.personAssembler = personAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHomeAsHtml() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("persons", personAssembler.toResources(personRepository.findAll()));
        return modelAndView;
    }


}
