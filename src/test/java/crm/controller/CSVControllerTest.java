package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CSVControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CSVController csvController;

    private Customer testCustomer;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test Customer");
        testCustomer.setEmail("test@test.com");
        testCustomer.setPhone(123456789);
        testCustomer.setFirstName("John");
        testCustomer.setLastName("Doe");
        testCustomer.setCity("NYC");
        testCustomer.setAddress("123 St");
        testCustomer.setEnabled(1);
        
        response = new MockHttpServletResponse();
    }

    @Test
    void testDownloadCSV() throws IOException {
        // Arrange
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList(testCustomer));

        // Act
        csvController.downloadCSV(response);

        // Assert
        verify(customerService, times(1)).listAllCustomers();
        assertEquals("text/csv", response.getContentType());
        assertTrue(response.getHeader("Content-Disposition").contains("customers.csv"));
    }

    @Test
    void testDownloadCSV_EmptyList() throws IOException {
        // Arrange
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList());

        // Act
        csvController.downloadCSV(response);

        // Assert
        verify(customerService, times(1)).listAllCustomers();
        assertEquals("text/csv", response.getContentType());
    }

    @Test
    void testDownloadCSV_MultipleCustomers() throws IOException {
        // Arrange
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Customer 2");
        customer2.setEmail("test2@test.com");
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList(testCustomer, customer2));

        // Act
        csvController.downloadCSV(response);

        // Assert
        verify(customerService, times(1)).listAllCustomers();
        assertEquals("text/csv", response.getContentType());
    }

    @Test
    void testDownloadCSV_ResponseNotNull() throws IOException {
        // Arrange
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList(testCustomer));

        // Act
        csvController.downloadCSV(response);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getContentType());
    }
}
