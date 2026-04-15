package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExportCustomersTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @InjectMocks
    private ExportCustomers exportCustomers;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test Customer");
        testCustomer.setEmail("test@test.com");
    }

    @Test
    void testExportCustomersToCsv() {
        // Arrange
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList(testCustomer));

        // Act
        String viewName = exportCustomers.exportCustomersToCsv(model);

        // Assert
        assertEquals("csvView", viewName);
        verify(model, times(1)).addAttribute(eq("customers"), any());
        verify(customerService, times(1)).listAllCustomers();
    }

    @Test
    void testExportCustomersToCsv_EmptyList() {
        // Arrange
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList());

        // Act
        String viewName = exportCustomers.exportCustomersToCsv(model);

        // Assert
        assertEquals("csvView", viewName);
        verify(customerService, times(1)).listAllCustomers();
    }

    @Test
    void testExportCustomersToCsv_MultipleCustomers() {
        // Arrange
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Customer 2");
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList(testCustomer, customer2));

        // Act
        String viewName = exportCustomers.exportCustomersToCsv(model);

        // Assert
        assertEquals("csvView", viewName);
        verify(customerService, times(1)).listAllCustomers();
    }
}
