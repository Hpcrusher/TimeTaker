package timetakers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timetakers.exception.ValidationRuntimeException;
import timetakers.model.Person;
import timetakers.model.User;
import timetakers.repository.PersonRepository;
import timetakers.repository.UserRepository;
import timetakers.util.TextKey;
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

    public void register(final SignupDto signupDto) {
        if (signupDto.username.trim().isEmpty()) {
            throw new ValidationRuntimeException(new TextKey("validation.notEmpty"), "username");
        }
        if (signupDto.password.trim().isEmpty()) {
            throw new ValidationRuntimeException(new TextKey("validation.notEmpty"), "password");
        }
        if (signupDto.firstname.trim().isEmpty()) {
            throw new ValidationRuntimeException(new TextKey("validation.notEmpty"), "firstname");
        }
        if (signupDto.name.trim().isEmpty()) {
            throw new ValidationRuntimeException(new TextKey("validation.notEmpty"), "name");
        }
        if (userRepository.findByUserName(signupDto.username) != null) {
            throw new ValidationRuntimeException(new TextKey("validation.usernameAlreadyExists"), "username");
        }
        Person person = Person.builder().setName(signupDto.name.trim()).setFirstname(signupDto.firstname.trim()).createPerson();
        personRepository.save(person);
        User user = User.builder().setPerson(person).setUserName(signupDto.username.trim()).setPassword(signupDto.password.trim()).createUser();
        userRepository.save(user);
    }

}
