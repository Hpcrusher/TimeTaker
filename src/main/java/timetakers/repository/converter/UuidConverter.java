package timetakers.repository.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

/**
 * @author David Liebl
 */

@Converter(autoApply = true)
public class UuidConverter implements AttributeConverter<UUID, String> {



    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return uuid.toString().toUpperCase();
    }

    @Override
    public UUID convertToEntityAttribute(String data) {
        return null != data ? UUID.fromString(data) : null;
    }
}
