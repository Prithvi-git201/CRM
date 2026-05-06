package crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Cloud-ready date/time controller using java.time API.
 * All timestamps are standardized to UTC to avoid timezone issues in distributed cloud environments.
 */
@Controller
@RequestMapping("/date")
public class DateTimeTestController {

    // Use UTC clock for all time operations to ensure consistency across cloud regions
    private static final Clock UTC_CLOCK = Clock.systemUTC();
    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");

    @GetMapping("/test")
    public String dateTimeTest(Model model) {
        // Use Instant for timestamps - always in UTC
        Instant currentInstant = Instant.now(UTC_CLOCK);
        
        // Use ZonedDateTime with explicit UTC timezone for cloud consistency
        ZonedDateTime utcDateTime = ZonedDateTime.now(UTC_CLOCK);
        
        // LocalDateTime in UTC context
        LocalDateTime localDateTime = LocalDateTime.now(UTC_CLOCK);
        
        // LocalDate in UTC context
        LocalDate localDate = LocalDate.now(UTC_CLOCK);
        
        // Add attributes with cloud-ready time values
        model.addAttribute("timestamp", currentInstant);
        model.addAttribute("utcDateTime", utcDateTime);
        model.addAttribute("localDateTime", localDateTime);
        model.addAttribute("localDate", localDate);
        
        // For backward compatibility, convert to legacy Date if needed
        // But prefer using Instant/ZonedDateTime in new code
        model.addAttribute("instantAsEpochMilli", currentInstant.toEpochMilli());
        model.addAttribute("timezone", "UTC");
        
        return "date/test";
    }

    /**
     * Example method showing how to handle timezone conversions in cloud environments.
     * Always store in UTC, convert to user timezone only for display.
     */
    public ZonedDateTime convertToUserTimezone(Instant utcInstant, String userTimezoneId) {
        ZoneId userZone = ZoneId.of(userTimezoneId);
        return utcInstant.atZone(userZone);
    }

    /**
     * Example method for scheduled operations using java.time API.
     * Replace java.util.Timer with ScheduledExecutorService or Spring @Scheduled.
     */
    public Instant getNextScheduledTime(int hoursFromNow) {
        return Instant.now(UTC_CLOCK).plusSeconds(hoursFromNow * 3600L);
    }
}
