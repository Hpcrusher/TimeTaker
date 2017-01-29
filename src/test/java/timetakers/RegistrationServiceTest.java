/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import timetakers.model.Person;
import timetakers.model.User;
import timetakers.repository.PersonRepository;
import timetakers.repository.UserRepository;
import timetakers.services.RegistrationService;
import timetakers.web.model.SignupDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author David Liebl
 */

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private UserRepository userRepository;

    private RegistrationService sut;
    private SignupDto signupDto;

    @Before
    public void setUp() throws Exception {
        signupDto = new SignupDto();
        signupDto.username = "Hpcrusher";
        signupDto.password = "123";
        signupDto.firstname = "David";
        signupDto.name = "Liebl";
        sut = new RegistrationService(personRepository, userRepository);
    }

    @Test
    public void testRegistration() throws Exception {
        sut.register(signupDto);
        verify(personRepository, times(1)).save(any(Person.class));
        verify(userRepository, times(1)).save(any(User.class));
    }
}
