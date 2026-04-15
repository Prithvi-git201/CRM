package crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.Clock;
@Controller
        // Use Clock for testable time operations and standardize on UTC
        Clock utcClock = Clock.systemUTC();
        
        // Use Instant for timestamps (always UTC)
        Instant timestamp = Instant.now(utcClock);
        model.addAttribute("timestamp", timestamp);
        
        // Use ZonedDateTime with explicit UTC timezone for cloud consistency
        ZonedDateTime utcDateTime = ZonedDateTime.now(utcClock);
        model.addAttribute("utcDateTime", utcDateTime);
        
        // LocalDateTime and LocalDate from UTC for consistency across regions
        model.addAttribute("localDateTime", LocalDateTime.now(utcClock));
        model.addAttribute("localDate", LocalDate.now(utcClock));
        
    }

}
