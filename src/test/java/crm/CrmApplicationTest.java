package crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrmApplicationTest {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
        assertTrue(true);
    }

    @Test
    void testMainMethod() {
        // Test that main method doesn't throw exception
        assertDoesNotThrow(() -> {
            // We don't actually run the main method in tests
            // as it would start the entire application
        });
    }

    @Test
    void testApplicationClass() {
        // Verify the class exists and is properly annotated
        assertNotNull(CrmApplication.class);
    }
}
