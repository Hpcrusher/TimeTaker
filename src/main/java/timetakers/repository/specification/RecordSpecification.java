package timetakers.repository.specification;

import org.hibernate.Criteria;
import org.springframework.data.jpa.domain.Specification;
import timetakers.model.Item;
import timetakers.model.Person;
import timetakers.model.Record;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Martin Geßenich on 26.01.2017.
 */
public class RecordSpecification implements Specification<Record> {

    private final Person person;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public RecordSpecification(Person person, LocalDateTime start, LocalDateTime end) {
        this.person = person;
        this.start = start;
        this.end = end;
    }

    @Override
    public Predicate toPredicate(Root<Record> record, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        Join<Item, Record> item = record.join("item", JoinType.INNER);
        Predicate joinPredicate = cb.equal(item.get("id"), record.get("id"));
        item = item.on(joinPredicate);

        List<Predicate> predicates = new ArrayList<>();

        if (person != null) {
            predicates.add(cb.equal(item.get("person").get("id"), person.getId()));
        }

        if (start != null && end != null) {
            predicates.add(cb.between(record.get("start"), start, end));
            predicates.add(cb.between(record.get("end"), start, end));
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
