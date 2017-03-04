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
        return someday.withHour(0).withMinute(0).withSecond(1);
    }

    public static LocalDateTime getStartOfToday() {
        return startOfDayFrom(now());
    }

    public static LocalDateTime endOfDayFrom(LocalDateTime someday) {
        return startOfDayFrom(someday.withHour(23).withMinute(59).withSecond(59));
    }
}
