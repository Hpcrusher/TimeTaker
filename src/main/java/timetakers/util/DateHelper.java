package timetakers.util;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Created by Martin Geßenich on 02.02.2017.
 */
public class DateHelper {

    public static LocalDateTime now() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    public static LocalDateTime startOfDayFrom(LocalDateTime someday) {
        return LocalDateTime.of(someday.getYear(), someday.getMonth(), someday.getDayOfMonth(), 0, 0, 0);
    }

    public static LocalDateTime getStartOfToday() {
        return startOfDayFrom(now());
    }
}
