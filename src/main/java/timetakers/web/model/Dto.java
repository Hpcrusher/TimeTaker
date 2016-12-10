package timetakers.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author David Liebl
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Dto extends ResourceSupport {

}
