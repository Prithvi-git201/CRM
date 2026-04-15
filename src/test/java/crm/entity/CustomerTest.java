package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
    }

    @Test
    void testCustomerCreation() {
        // Arrange & Act
        Customer newCustomer = new Customer();
        
        // Assert
        assertNotNull(newCustomer);
    }

    @Test
    void testCustomerBuilderPattern() {
        // Arrange & Act
        Customer builtCustomer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("test@example.com")
                .phone(123456789)
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .address("123 Main St")
                .enabled(1)
                .build();
        
        // Assert
        assertNotNull(builtCustomer);
        assertEquals(1L, builtCustomer.getId());
        assertEquals("Test Customer", builtCustomer.getName());
        assertEquals("test@example.com", builtCustomer.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Set<Category> categories = new HashSet<>();
        
        // Act
        Customer customer = new Customer(1L, "Test", "test@test.com", 123456, 
                                        categories, "John", "Doe", "NYC", "123 St", 1);
        
        // Assert
        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertEquals("Test", customer.getName());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 1L;
        
        // Act
        customer.setId(expectedId);
        
        // Assert
        assertEquals(expectedId, customer.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "Test Customer";
        
        // Act
        customer.setName(expectedName);
        
        // Assert
        assertEquals(expectedName, customer.getName());
    }

    @Test
    void testSetAndGetEmail() {
        // Arrange
        String expectedEmail = "test@example.com";
        
        // Act
        customer.setEmail(expectedEmail);
        
        // Assert
        assertEquals(expectedEmail, customer.getEmail());
    }

    @Test
    void testSetAndGetPhone() {
        // Arrange
        int expectedPhone = 123456789;
        
        // Act
        customer.setPhone(expectedPhone);
        
        // Assert
        assertEquals(expectedPhone, customer.getPhone());
    }

    @Test
    void testSetAndGetFirstName() {
        // Arrange
        String expectedFirstName = "John";
        
        // Act
        customer.setFirstName(expectedFirstName);
        
        // Assert
        assertEquals(expectedFirstName, customer.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        // Arrange
        String expectedLastName = "Doe";
        
        // Act
        customer.setLastName(expectedLastName);
        
        // Assert
        assertEquals(expectedLastName, customer.getLastName());
    }

    @Test
    void testSetAndGetCity() {
        // Arrange
        String expectedCity = "New York";
        
        // Act
        customer.setCity(expectedCity);
        
        // Assert
        assertEquals(expectedCity, customer.getCity());
    }

    @Test
    void testSetAndGetAddress() {
        // Arrange
        String expectedAddress = "123 Main Street";
        
        // Act
        customer.setAddress(expectedAddress);
        
        // Assert
        assertEquals(expectedAddress, customer.getAddress());
    }

    @Test
    void testSetAndGetEnabled() {
        // Arrange
        int expectedEnabled = 1;
        
        // Act
        customer.setEnabled(expectedEnabled);
        
        // Assert
        assertEquals(expectedEnabled, customer.getEnabled());
    }

    @Test
    void testSetAndGetCategories() {
        // Arrange
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Premium");
        categories.add(category);
        
        // Act
        customer.setCategories(categories);
        
        // Assert
        assertNotNull(customer.getCategories());
        assertEquals(1, customer.getCategories().size());
    }

    @Test
    void testCustomerWithNullEmail() {
        // Arrange & Act
        customer.setEmail(null);
        
        // Assert
        assertNull(customer.getEmail());
    }

    @Test
    void testCustomerWithEmptyName() {
        // Arrange & Act
        customer.setName("");
        
        // Assert
        assertEquals("", customer.getName());
    }

    @Test
    void testCustomerWithZeroPhone() {
        // Arrange & Act
        customer.setPhone(0);
        
        // Assert
        assertEquals(0, customer.getPhone());
    }

    @Test
    void testCustomerWithNegativePhone() {
        // Arrange & Act
        customer.setPhone(-123);
        
        // Assert
        assertEquals(-123, customer.getPhone());
    }

    @Test
    void testCustomerEnabledFlag() {
        // Arrange & Act
        customer.setEnabled(0);
        
        // Assert
        assertEquals(0, customer.getEnabled());
    }

    @Test
    void testCustomerEquality() {
        // Arrange
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("Test")
                .email("test@test.com")
                .build();
        
        Customer customer2 = Customer.builder()
                .id(1L)
                .name("Test")
                .email("test@test.com")
                .build();
        
        // Assert
        assertEquals(customer1, customer2);
    }

    @Test
    void testCustomerToString() {
        // Arrange
        customer.setId(1L);
        customer.setName("Test Customer");
        customer.setEmail("test@test.com");
        
        // Act
        String result = customer.toString();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Test Customer"));
    }
}
