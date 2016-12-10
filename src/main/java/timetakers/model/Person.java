package timetakers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @author David Liebl
 */

@Entity
@Table(name = "person")
public class Person extends AbstractIdEntity {

    @Column(name = "name")
    private String name;

    public Person() {
    }

    public Person(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String name;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Person createPerson() {
            return new Person(id, name);
        }
    }

}
