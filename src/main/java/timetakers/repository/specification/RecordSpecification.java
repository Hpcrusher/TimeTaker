package timetakers.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import timetakers.model.Item;
import timetakers.model.Person;
import timetakers.model.Record;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Geßenich
 */
public class RecordSpecification implements Specification<Record> {

    private final Person person;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final boolean hasNoEndTime;

    public RecordSpecification(Person person, LocalDateTime start, LocalDateTime end) {
        this.person = person;
        this.start = start;
        this.end = end;
        this.hasNoEndTime = false;
    }

    /**
     * Constructor to find all records of person that
     * @param person
     */
    public RecordSpecification(Person person, boolean hasNoEndTime) {
        this.person = person;
        this.start = null;
        this.end = null;
        this.hasNoEndTime = hasNoEndTime;
    }

    @Override
    public Predicate toPredicate(Root<Record> record, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        Join<Item, Record> item = record.join("item", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        // find record that belong to person
        if (person != null) {
            predicates.add(cb.equal(item.get("person"), person));
        }

        // find records that started and ended between the start and end times
        if (start != null && end != null) {
            predicates.add(cb.between(record.get("start"), start, end));
            predicates.add(cb.between(record.get("end"), start, end));
        }

        // find records that have no end time
        if (hasNoEndTime){
            predicates.add(cb.isNull(record.get("end")));
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
