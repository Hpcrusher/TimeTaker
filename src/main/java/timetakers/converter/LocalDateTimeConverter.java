package timetakers.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author vm
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final DateTimeFormatter formatter;

    public LocalDateTimeConverter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public LocalDateTime convert(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(s, formatter);
    }

}
