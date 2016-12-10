package timetakers.web.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import timetakers.model.Person;
import timetakers.web.controller.PersonController;
import timetakers.web.model.PersonDto;

/**
 * @author David Liebl
 */

@Component
public class PersonAssembler extends ResourceAssemblerSupport<Person, PersonDto> {

    public PersonAssembler() {
        super(PersonController.class, PersonDto.class);
    }

    @Override
    public PersonDto toResource(Person person) {
        PersonDto dto  = new PersonDto();
        dto.oid = person.getId();
        dto.name = person.getName();
        return dto;
    }

}
