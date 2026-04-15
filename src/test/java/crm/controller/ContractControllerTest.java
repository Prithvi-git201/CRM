package crm.controller;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
import crm.entity.User;
import crm.service.ContractService;
import crm.service.CustomerService;
import crm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractControllerTest {

    @Mock
    private ContractService contractService;

    @Mock
    private CustomerService customerService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ContractController contractController;

    private Contract testContract;
    private Customer testCustomer;
    private User testUser;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test Customer");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testContract = new Contract();
        testContract.setId(1L);
        testContract.setName("Test Contract");
        testContract.setValue(new BigDecimal("10000.00"));
        testContract.setBeginDate(LocalDate.of(2024, 1, 1));
        testContract.setEndDate(LocalDate.of(2024, 12, 31));
        testContract.setStatus(Status.PROPOSED);
        testContract.setCustomer(testCustomer);
        testContract.setUser(testUser);
    }

    @Test
    void testShowAllContracts() {
        // Arrange
        when(contractService.listAllContracts()).thenReturn(Arrays.asList(testContract));

        // Act
        String viewName = contractController.showAllContracts(model);

        // Assert
        assertEquals("contract/list", viewName);
        verify(model, times(1)).addAttribute(eq("contracts"), any());
        verify(contractService, times(1)).listAllContracts();
    }

    @Test
    void testShowFormAddContract() {
        // Arrange
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList(testCustomer));
        when(userService.listAllUsers()).thenReturn(Arrays.asList(testUser));

        // Act
        String viewName = contractController.showFormAddContract(model);

        // Assert
        assertEquals("contract/add", viewName);
        verify(model, times(1)).addAttribute(eq("contract"), any(Contract.class));
        verify(model, times(1)).addAttribute(eq("customers"), any());
        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(model, times(1)).addAttribute(eq("statuses"), any());
    }

    @Test
    void testProcessRequestAddContract_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(contractService).saveContract(any(Contract.class));

        // Act
        String viewName = contractController.processRequestAddContract(testContract, bindingResult);

        // Assert
        assertEquals("contract/success", viewName);
        verify(contractService, times(1)).saveContract(testContract);
    }

    @Test
    void testProcessRequestAddContract_WithErrors() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = contractController.processRequestAddContract(testContract, bindingResult);

        // Assert
        assertEquals("redirect:/contract/add", viewName);
        verify(contractService, never()).saveContract(any(Contract.class));
    }

    @Test
    void testShowFormEditContract() {
        // Arrange
        when(contractService.showContract(1L)).thenReturn(testContract);
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList(testCustomer));
        when(userService.listAllUsers()).thenReturn(Arrays.asList(testUser));

        // Act
        String viewName = contractController.showFormEditContract(model, 1L);

        // Assert
        assertEquals("contract/edit", viewName);
        verify(model, times(1)).addAttribute("contract", testContract);
        verify(contractService, times(1)).showContract(1L);
    }

    @Test
    void testProcessRequestEditContract_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(contractService).saveContract(any(Contract.class));

        // Act
        String viewName = contractController.processRequestEditContract(1L, testContract, bindingResult);

        // Assert
        assertEquals("redirect:/contract/list", viewName);
        verify(contractService, times(1)).saveContract(testContract);
    }

    @Test
    void testProcessRequestEditContract_WithErrors() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = contractController.processRequestEditContract(1L, testContract, bindingResult);

        // Assert
        assertEquals("redirect:/contract/edit/1", viewName);
        verify(contractService, never()).saveContract(any(Contract.class));
    }

    @Test
    void testShowFormSearchByName() {
        // Act
        String viewName = contractController.showFormSearchByName(model);

        // Assert
        assertEquals("contract/name-search", viewName);
        verify(model, times(1)).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestSearchByName() {
        // Arrange
        when(contractService.findByName("Test Contract")).thenReturn(testContract);

        // Act
        String viewName = contractController.processRequestSearchByName(testContract, model);

        // Assert
        assertEquals("contract/show-one", viewName);
        verify(model, times(1)).addAttribute(eq("contract"), any());
        verify(contractService, times(1)).findByName(anyString());
    }

    @Test
    void testShowFormSearchByValue() {
        // Act
        String viewName = contractController.showFormSearchByValue(model);

        // Assert
        assertEquals("contract/value-search", viewName);
        verify(model, times(1)).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testShowFormSearchByBeginDate() {
        // Act
        String viewName = contractController.showFormSearchByBeginDate(model);

        // Assert
        assertEquals("contract/begin-date-search", viewName);
        verify(model, times(1)).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testShowFormSearchByEndDate() {
        // Act
        String viewName = contractController.showFormSearchByEndDate(model);

        // Assert
        assertEquals("contract/end-date-search", viewName);
        verify(model, times(1)).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testShowFormSearchByStatus() {
        // Act
        String viewName = contractController.showFormSearchByStatus(model);

        // Assert
        assertEquals("contract/status-search", viewName);
        verify(model, times(1)).addAttribute(eq("contract"), any(Contract.class));
        verify(model, times(1)).addAttribute(eq("statuses"), any());
    }

    @Test
    void testShowFormSearchByCustomer() {
        // Arrange
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList(testCustomer));

        // Act
        String viewName = contractController.showFormSearchByCustomer(model);

        // Assert
        assertEquals("contract/customer-search", viewName);
        verify(model, times(1)).addAttribute(eq("contract"), any(Contract.class));
        verify(model, times(1)).addAttribute(eq("customers"), any());
    }

    @Test
    void testShowFormSearchByUser() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(Arrays.asList(testUser));

        // Act
        String viewName = contractController.showFormSearchByUser(model);

        // Assert
        assertEquals("contract/user-search", viewName);
        verify(model, times(1)).addAttribute(eq("contract"), any(Contract.class));
        verify(model, times(1)).addAttribute(eq("users"), any());
    }
}
