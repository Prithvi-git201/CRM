package crm.service;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
import crm.entity.User;
import crm.repository.ContractRepository;
import crm.repository.CustomerRepository;
import crm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContractServiceImpl contractService;

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
    void testFindByName_Success() {
        // Arrange
        when(contractRepository.findByName("Test Contract")).thenReturn(testContract);

        // Act
        Contract result = contractService.findByName("Test Contract");

        // Assert
        assertNotNull(result);
        assertEquals("Test Contract", result.getName());
        verify(contractRepository, times(1)).findByName("Test Contract");
    }

    @Test
    void testFindByName_NotFound() {
        // Arrange
        when(contractRepository.findByName("Nonexistent")).thenReturn(null);

        // Act
        Contract result = contractService.findByName("Nonexistent");

        // Assert
        assertNull(result);
        verify(contractRepository, times(1)).findByName("Nonexistent");
    }

    @Test
    void testListAllContracts() {
        // Arrange
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAll()).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.listAllContracts();

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAll();
    }

    @Test
    void testShowContract_Found() {
        // Arrange
        when(contractRepository.findById(1L)).thenReturn(Optional.of(testContract));

        // Act
        Contract result = contractService.showContract(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(contractRepository, times(1)).findById(1L);
    }

    @Test
    void testShowContract_NotFound() {
        // Arrange
        when(contractRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Contract result = contractService.showContract(999L);

        // Assert
        assertNull(result);
        verify(contractRepository, times(1)).findById(999L);
    }

    @Test
    void testFindAllByValueLessThanEqual() {
        // Arrange
        BigDecimal value = new BigDecimal("15000.00");
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByValueLessThanEqual(value)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByValueLessThanEqual(value);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByValueLessThanEqual(value);
    }

    @Test
    void testFindAllByValueGreaterThanEqual() {
        // Arrange
        BigDecimal value = new BigDecimal("5000.00");
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByValueGreaterThanEqual(value)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByValueGreaterThanEqual(value);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByValueGreaterThanEqual(value);
    }

    @Test
    void testFindAllByBeginDate() {
        // Arrange
        LocalDate beginDate = LocalDate.of(2024, 1, 1);
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByBeginDate(beginDate)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByBeginDate(beginDate);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByBeginDate(beginDate);
    }

    @Test
    void testFindAllByBeginDateBefore() {
        // Arrange
        LocalDate beforeDate = LocalDate.of(2024, 6, 1);
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByBeginDateBefore(beforeDate)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByBeginDateBefore(beforeDate);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByBeginDateBefore(beforeDate);
    }

    @Test
    void testFindAllByBeginDateAfter() {
        // Arrange
        LocalDate afterDate = LocalDate.of(2023, 12, 1);
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByBeginDateAfter(afterDate)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByBeginDateAfter(afterDate);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByBeginDateAfter(afterDate);
    }

    @Test
    void testFindAllByEndDate() {
        // Arrange
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByEndDate(endDate)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByEndDate(endDate);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByEndDate(endDate);
    }

    @Test
    void testFindAllByEndDateBefore() {
        // Arrange
        LocalDate beforeDate = LocalDate.of(2025, 1, 1);
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByEndDateBefore(beforeDate)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByEndDateBefore(beforeDate);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByEndDateBefore(beforeDate);
    }

    @Test
    void testFindAllByEndDateAfter() {
        // Arrange
        LocalDate afterDate = LocalDate.of(2024, 6, 1);
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByEndDateAfter(afterDate)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByEndDateAfter(afterDate);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByEndDateAfter(afterDate);
    }

    @Test
    void testFindAllByStatus() {
        // Arrange
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByStatus(Status.PROPOSED)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByStatus(Status.PROPOSED);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByStatus(Status.PROPOSED);
    }

    @Test
    void testFindAllByCustomer() {
        // Arrange
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByCustomer(testCustomer)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByCustomer(testCustomer);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByCustomer(testCustomer);
    }

    @Test
    void testFindAllByCustomerAndUser() {
        // Arrange
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByCustomerAndUser(testCustomer, testUser)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByCustomerAndUser(testCustomer, testUser);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByCustomerAndUser(testCustomer, testUser);
    }

    @Test
    void testFindAllByUser() {
        // Arrange
        List<Contract> contracts = Arrays.asList(testContract);
        when(contractRepository.findAllByUser(testUser)).thenReturn(contracts);

        // Act
        Iterable<Contract> result = contractService.findAllByUser(testUser);

        // Assert
        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByUser(testUser);
    }

    @Test
    void testSaveContract() {
        // Arrange
        when(customerRepository.findAll()).thenReturn(Arrays.asList(testCustomer));
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));
        when(customerRepository.saveAll(any())).thenReturn(Arrays.asList(testCustomer));
        when(userRepository.saveAll(any())).thenReturn(Arrays.asList(testUser));
        when(contractRepository.save(any(Contract.class))).thenReturn(testContract);

        // Act
        contractService.saveContract(testContract);

        // Assert
        verify(customerRepository, times(1)).saveAll(any());
        verify(userRepository, times(1)).saveAll(any());
        verify(contractRepository, times(1)).save(testContract);
    }
}
