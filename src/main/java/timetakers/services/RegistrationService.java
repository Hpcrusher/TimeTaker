package timetakers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timetakers.model.Person;
import timetakers.model.User;
import timetakers.repository.PersonRepository;
import timetakers.repository.UserRepository;
import timetakers.web.assembler.PersonAssembler;
import timetakers.web.model.SignupDto;

/**
 * @author vm
 */

@Service
public class RegistrationService {

    private PersonRepository personRepository;
    private UserRepository userRepository;

    @Autowired
    public RegistrationService(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public void register(SignupDto signupDto) {
        Person person = Person.builder().setName(signupDto.name).setFirstname(signupDto.firstname).createPerson();
        personRepository.save(person);
        User user = User.builder().setPerson(person).setUserName(signupDto.username).setPassword(signupDto.password).createUser();
        userRepository.save(user);
    }

}
