package crm.view;

import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CsvViewTest {

    private CsvView csvView;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Map<String, Object> model;

    @BeforeEach
    void setUp() {
        csvView = new CsvView();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        model = new HashMap<>();
    }

    @Test
    void testBuildCsvDocument_WithUsers() throws Exception {
        // Arrange
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john@test.com");
        user.setPassword("password");
        user.setEnabled(1);
        users.add(user);
        
        model.put("users", users);

        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        assertNotNull(response.getContentAsString());
        assertTrue(response.getHeader("Content-Disposition").contains("my-csv-file.csv"));
    }

    @Test
    void testBuildCsvDocument_EmptyUserList() throws Exception {
        // Arrange
        List<User> users = new ArrayList<>();
        model.put("users", users);

        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        assertNotNull(response.getContentAsString());
    }

    @Test
    void testContentType() {
        // Act
        String contentType = csvView.getContentType();

        // Assert
        assertEquals("text/csv", contentType);
    }
}
