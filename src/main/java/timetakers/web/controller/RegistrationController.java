/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import timetakers.model.Person;
import timetakers.model.User;
import timetakers.repository.PersonRepository;
import timetakers.repository.UserRepository;
import timetakers.web.assembler.PersonAssembler;

/**
 * @author David Liebl
 */

@Controller
@Transactional
@RequestMapping(value = "/signup")
public class RegistrationController {

    private PersonRepository personRepository;
    private UserRepository userRepository;

    @Autowired
    public RegistrationController(PersonRepository personRepository, UserRepository userRepository, PersonAssembler personAssembler) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public void createPerson(@RequestParam String name, @RequestParam String username, @RequestParam String password) {
        Person person = Person.builder().setName(name).createPerson();
        personRepository.save(person);
        User user = User.builder().setPerson(person).setUserName(username).setPassword(password).createUser();
        userRepository.save(user);
    }

}
