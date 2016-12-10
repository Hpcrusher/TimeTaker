package timetakers.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.UUID;

/**
 * @author David Liebl
 */

@MappedSuperclass
public abstract class AbstractIdEntity {

    @Id
    @Column(name = "id")
    @Type(type = "timetakers.repository.converter.UuidType")
    protected UUID id;

    public UUID getId() {
        return id;
    }

    @Transient
    public String getIdString() {
        return id != null ? id.toString() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractIdEntity that = (AbstractIdEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
