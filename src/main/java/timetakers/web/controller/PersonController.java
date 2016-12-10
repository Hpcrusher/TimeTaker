package timetakers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import timetakers.model.Person;
import timetakers.repository.PersonRepository;
import timetakers.web.assembler.PersonAssembler;
import timetakers.web.model.PersonDto;

import java.util.List;
import java.util.UUID;


/**
 * @author David Liebl
 */

@Controller
@Transactional
@RequestMapping(value = "/person")
public class PersonController {

    private PersonRepository personRepository;
    private PersonAssembler personAssembler;

    @Autowired
    public PersonController(PersonRepository personRepository, PersonAssembler personAssembler) {
        this.personRepository = personRepository;
        this.personAssembler = personAssembler;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    @ResponseBody
    public void createPerson(@RequestParam String name) {
        personRepository.save(Person.builder().setName(name).createPerson());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<PersonDto> getPersonsAsJson() {
        return personAssembler.toResources(personRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDto getPersonWithIdAsJson(@PathVariable UUID id) {
        return personAssembler.toResource(personRepository.findOne(id));
    }

}
