package crm.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.web.servlet.error.ErrorController;

import static org.junit.jupiter.api.Assertions.*;

class MyErrorControllerTest {

    private MyErrorController errorController;

    @BeforeEach
    void setUp() {
        errorController = new MyErrorController();
    }

    @Test
    void testHandleError() {
        // Act
        String result = errorController.handleError();

        // Assert
        assertEquals("error", result);
    }

    @Test
    void testImplementsErrorController() {
        // Assert
        assertInstanceOf(ErrorController.class, errorController);
    }

    @Test
    void testErrorControllerNotNull() {
        // Assert
        assertNotNull(errorController);
    }
}
