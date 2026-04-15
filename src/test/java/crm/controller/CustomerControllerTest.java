package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test Customer");
        testCustomer.setEmail("test@test.com");
        testCustomer.setPhone(123456789);
        testCustomer.setEnabled(1);
    }

    @Test
    void testShowAllCustomers() {
        // Arrange
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList(testCustomer));

        // Act
        String viewName = customerController.showAllCustomers(model);

        // Assert
        assertEquals("customer/list", viewName);
        verify(model, times(1)).addAttribute(eq("customers"), any());
        verify(customerService, times(1)).listAllCustomers();
    }

    @Test
    void testShowFormAddCustomer() {
        // Act
        String viewName = customerController.showFormAddCustomer(model);

        // Assert
        assertEquals("customer/add", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestAddCustomer_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(customerService).saveCustomer(any(Customer.class));

        // Act
        String viewName = customerController.processRequestAddCustomer(testCustomer, bindingResult);

        // Assert
        assertEquals("customer/success", viewName);
        verify(customerService, times(1)).saveCustomer(testCustomer);
    }

    @Test
    void testProcessRequestAddCustomer_WithErrors() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = customerController.processRequestAddCustomer(testCustomer, bindingResult);

        // Assert
        assertEquals("redirect:/customer/add", viewName);
        verify(customerService, never()).saveCustomer(any(Customer.class));
    }

    @Test
    void testShowFormEditCustomer() {
        // Arrange
        when(customerService.showCustomer(1L)).thenReturn(testCustomer);

        // Act
        String viewName = customerController.showFormEditCustomer(model, 1L);

        // Assert
        assertEquals("customer/edit", viewName);
        verify(model, times(1)).addAttribute("customer", testCustomer);
        verify(customerService, times(1)).showCustomer(1L);
    }

    @Test
    void testProcessRequestEditCustomer_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(customerService).saveCustomer(any(Customer.class));

        // Act
        String viewName = customerController.processRequestEditCustomer(1L, testCustomer, bindingResult);

        // Assert
        assertEquals("redirect:/customer/list", viewName);
        verify(customerService, times(1)).saveCustomer(testCustomer);
    }

    @Test
    void testProcessRequestEditCustomer_WithErrors() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = customerController.processRequestEditCustomer(1L, testCustomer, bindingResult);

        // Assert
        assertEquals("redirect:/customer/edit/1", viewName);
        verify(customerService, never()).saveCustomer(any(Customer.class));
    }

    @Test
    void testShowFormCreateCustomerBasedOnAnotherOne() {
        // Arrange
        when(customerService.showCustomer(1L)).thenReturn(testCustomer);

        // Act
        String viewName = customerController.showFormCreateCustomerBasedOnAnotherOne(model, 1L);

        // Assert
        assertEquals("customer/add-customer-based-on-another-one", viewName);
        verify(model, times(1)).addAttribute("customer", testCustomer);
        verify(customerService, times(1)).showCustomer(1L);
    }

    @Test
    void testCreateCustomerBasedOnAnotherOne_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(customerService.getMaxId()).thenReturn(10L);
        doNothing().when(customerService).saveCustomer(any(Customer.class));

        // Act
        String viewName = customerController.createCustomerBasedOnAnotherOne(1L, testCustomer, bindingResult);

        // Assert
        assertEquals("redirect:/customer/list", viewName);
        verify(customerService, times(1)).getMaxId();
        verify(customerService, times(1)).saveCustomer(any(Customer.class));
    }

    @Test
    void testShowNameSearchForm() {
        // Act
        String viewName = customerController.showNameSearchForm(model);

        // Assert
        assertEquals("customer/name-search", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestNameSearch() {
        // Arrange
        when(customerService.findOneByEnabledTrueAndName("Test Customer")).thenReturn(testCustomer);

        // Act
        String viewName = customerController.processRequestNameSearch(testCustomer, model);

        // Assert
        assertEquals("customer/show-one", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), any());
        verify(customerService, times(1)).findOneByEnabledTrueAndName(anyString());
    }

    @Test
    void testShowEmailSearchForm() {
        // Act
        String viewName = customerController.showEmailSearchForm(model);

        // Assert
        assertEquals("customer/email-search", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestEmailSearch() {
        // Arrange
        when(customerService.findByEnabledTrueAndEmail("test@test.com")).thenReturn(Arrays.asList(testCustomer));

        // Act
        String viewName = customerController.processRequestEmailSearch(testCustomer, model);

        // Assert
        assertEquals("customer/show-list", viewName);
        verify(model, times(1)).addAttribute(eq("customers"), any());
        verify(customerService, times(1)).findByEnabledTrueAndEmail(anyString());
    }

    @Test
    void testShowPhoneSearchForm() {
        // Act
        String viewName = customerController.showPhoneSearchForm(model);

        // Assert
        assertEquals("customer/phone-search", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestPhoneSearch() {
        // Arrange
        when(customerService.findByEnabledTrueAndPhone(123456789)).thenReturn(Arrays.asList(testCustomer));

        // Act
        String viewName = customerController.processRequestPhoneSearch(testCustomer, model);

        // Assert
        assertEquals("customer/show-list", viewName);
        verify(model, times(1)).addAttribute(eq("customers"), any());
        verify(customerService, times(1)).findByEnabledTrueAndPhone(anyInt());
    }

    @Test
    void testShowFirstNameSearchForm() {
        // Act
        String viewName = customerController.showFirstNameSearchForm(model);

        // Assert
        assertEquals("customer/first-name-search", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testShowLastNameSearchForm() {
        // Act
        String viewName = customerController.showLastNameSearchForm(model);

        // Assert
        assertEquals("customer/last-name-search", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testShowCitySearchForm() {
        // Act
        String viewName = customerController.showCitySearchForm(model);

        // Assert
        assertEquals("customer/city-search", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testShowCityAddressSearchForm() {
        // Act
        String viewName = customerController.showCityAddressSearchForm(model);

        // Assert
        assertEquals("customer/city-address-search", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), any(Customer.class));
    }
}
