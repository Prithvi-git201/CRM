package crm.service;

import crm.entity.Category;
import crm.entity.Customer;
import crm.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer testCustomer;
    private Set<Category> testCategories;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test Customer");
        testCustomer.setEmail("test@test.com");
        testCustomer.setPhone(123456789);
        testCustomer.setEnabled(1);

        testCategories = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Premium");
        testCategories.add(category);
    }

    @Test
    void testGetMaxId() {
        // Arrange
        when(customerRepository.getMaxId()).thenReturn(10L);

        // Act
        Long result = customerService.getMaxId();

        // Assert
        assertEquals(10L, result);
        verify(customerRepository, times(1)).getMaxId();
    }

    @Test
    void testListAllCustomers() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.listAllCustomers();

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testShowCustomer_Found() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        // Act
        Customer result = customerService.showCustomer(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testShowCustomer_NotFound() {
        // Arrange
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Customer result = customerService.showCustomer(999L);

        // Assert
        assertNull(result);
        verify(customerRepository, times(1)).findById(999L);
    }

    @Test
    void testFindAllByEnabledTrue() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findAllByEnabled(1)).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findAllByEnabledTrue();

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findAllByEnabled(1);
    }

    @Test
    void testFindAllByEnabledFalse() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findAllByEnabled(0)).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findAllByEnabledFalse();

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findAllByEnabled(0);
    }

    @Test
    void testFindOneByEnabledTrueAndName() {
        // Arrange
        when(customerRepository.findOneByEnabledAndName(1, "Test Customer")).thenReturn(testCustomer);

        // Act
        Customer result = customerService.findOneByEnabledTrueAndName("Test Customer");

        // Assert
        assertNotNull(result);
        assertEquals("Test Customer", result.getName());
        verify(customerRepository, times(1)).findOneByEnabledAndName(1, "Test Customer");
    }

    @Test
    void testFindOneByName() {
        // Arrange
        when(customerRepository.findOneByName("Test Customer")).thenReturn(testCustomer);

        // Act
        Customer result = customerService.findOneByName("Test Customer");

        // Assert
        assertNotNull(result);
        assertEquals("Test Customer", result.getName());
        verify(customerRepository, times(1)).findOneByName("Test Customer");
    }

    @Test
    void testFindByEnabledTrueAndEmail() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByEnabledAndEmail(1, "test@test.com")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByEnabledTrueAndEmail("test@test.com");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndEmail(1, "test@test.com");
    }

    @Test
    void testFindByEmail() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByEmail("test@test.com")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByEmail("test@test.com");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByEmail("test@test.com");
    }

    @Test
    void testFindByEnabledTrueAndPhone() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByEnabledAndPhone(1, 123456789)).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByEnabledTrueAndPhone(123456789);

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndPhone(1, 123456789);
    }

    @Test
    void testFindByPhone() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByPhone(123456789)).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByPhone(123456789);

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByPhone(123456789);
    }

    @Test
    void testFindByEnabledTrueAndCategories() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByEnabledAndCategories(1, testCategories)).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByEnabledTrueAndCategories(testCategories);

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndCategories(1, testCategories);
    }

    @Test
    void testFindByCategories() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByCategories(testCategories)).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByCategories(testCategories);

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByCategories(testCategories);
    }

    @Test
    void testFindByEnabledTrueAndFirstName() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByEnabledAndFirstName(1, "John")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByEnabledTrueAndFirstName("John");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndFirstName(1, "John");
    }

    @Test
    void testFindByFirstName() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByFirstName("John")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByFirstName("John");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByFirstName("John");
    }

    @Test
    void testFindByEnabledTrueAndLastName() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByEnabledAndLastName(1, "Doe")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByEnabledTrueAndLastName("Doe");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndLastName(1, "Doe");
    }

    @Test
    void testFindByLastName() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByLastName("Doe")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByLastName("Doe");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByLastName("Doe");
    }

    @Test
    void testFindByEnabledTrueAndFirstNameAndLastName() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByEnabledAndFirstNameAndLastName(1, "John", "Doe")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByEnabledTrueAndFirstNameAndLastName("John", "Doe");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndFirstNameAndLastName(1, "John", "Doe");
    }

    @Test
    void testFindByFirstNameAndLastName() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByFirstNameAndLastName("John", "Doe");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void testFindByEnabledTrueAndCity() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByEnabledAndCity(1, "New York")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByEnabledTrueAndCity("New York");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndCity(1, "New York");
    }

    @Test
    void testFindByCity() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByCity("New York")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByCity("New York");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByCity("New York");
    }

    @Test
    void testFindByEnabledTrueAndCityAndAddress() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByEnabledAndCityAndAddress(1, "New York", "123 Main St")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByEnabledTrueAndCityAndAddress("New York", "123 Main St");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndCityAndAddress(1, "New York", "123 Main St");
    }

    @Test
    void testFindByCityAndAddress() {
        // Arrange
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByCityAndAddress("New York", "123 Main St")).thenReturn(customers);

        // Act
        Iterable<Customer> result = customerService.findByCityAndAddress("New York", "123 Main St");

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findByCityAndAddress("New York", "123 Main St");
    }

    @Test
    void testSaveCustomer() {
        // Arrange
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        customerService.saveCustomer(testCustomer);

        // Assert
        assertEquals(1, testCustomer.getEnabled());
        verify(customerRepository, times(1)).save(testCustomer);
    }

    @Test
    void testSaveCustomer_SetsEnabledToOne() {
        // Arrange
        testCustomer.setEnabled(0);
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        customerService.saveCustomer(testCustomer);

        // Assert
        assertEquals(1, testCustomer.getEnabled());
        verify(customerRepository, times(1)).save(testCustomer);
    }
}
