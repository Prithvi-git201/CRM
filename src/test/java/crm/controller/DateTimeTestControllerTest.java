package crm.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.ui.Model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DateTimeTestControllerTest {

    private DateTimeTestController controller;
    private Model model;

    @BeforeEach
    void setUp() {
        controller = new DateTimeTestController();
        model = mock(Model.class);
    }

    @Test
    void testDateTimeTest() {
        // Act
        String viewName = controller.dateTimeTest(model);

        // Assert
        assertEquals("datetime-test", viewName);
        verify(model, times(1)).addAttribute(eq("now"), any(LocalDateTime.class));
    }

    @Test
    void testDateTimeTest_AddsCurrentTime() {
        // Act
        controller.dateTimeTest(model);

        // Assert
        verify(model, times(1)).addAttribute(eq("now"), any(LocalDateTime.class));
    }

    @Test
    void testDateTimeTest_NotNull() {
        // Act
        String result = controller.dateTimeTest(model);

        // Assert
        assertNotNull(result);
    }
}
