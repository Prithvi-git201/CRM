package crm.utils;

import crm.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriteCsvToResponseTest {

    private Customer testCustomer;
    private List<Customer> customers;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test Customer");
        testCustomer.setEmail("test@test.com");
        testCustomer.setPhone(123456789);
        testCustomer.setFirstName("John");
        testCustomer.setLastName("Doe");
        testCustomer.setCity("New York");
        testCustomer.setAddress("123 Main St");
        testCustomer.setEnabled(1);

        customers = new ArrayList<>();
        customers.add(testCustomer);

        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    @Test
    void testWriteCustomers_Success() {
        // Act
        WriteCsvToResponse.writeCustomers(printWriter, customers);
        printWriter.flush();
        String result = stringWriter.toString();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testWriteCustomers_EmptyList() {
        // Arrange
        List<Customer> emptyList = new ArrayList<>();

        // Act
        WriteCsvToResponse.writeCustomers(printWriter, emptyList);
        printWriter.flush();
        String result = stringWriter.toString();

        // Assert
        assertNotNull(result);
    }

    @Test
    void testWriteCustomers_MultipleCustomers() {
        // Arrange
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Customer 2");
        customer2.setEmail("test2@test.com");
        customer2.setPhone(987654321);
        customers.add(customer2);

        // Act
        WriteCsvToResponse.writeCustomers(printWriter, customers);
        printWriter.flush();
        String result = stringWriter.toString();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testWriteCustomer_Success() {
        // Act
        WriteCsvToResponse.writeCustomer(printWriter, testCustomer);
        printWriter.flush();
        String result = stringWriter.toString();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testWriteCustomer_WithNullFields() {
        // Arrange
        Customer customerWithNulls = new Customer();
        customerWithNulls.setId(1L);
        customerWithNulls.setName("Test");
        customerWithNulls.setEmail("test@test.com");

        // Act
        WriteCsvToResponse.writeCustomer(printWriter, customerWithNulls);
        printWriter.flush();
        String result = stringWriter.toString();

        // Assert
        assertNotNull(result);
    }

    @Test
    void testWriteCustomers_WithNullPrintWriter() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            WriteCsvToResponse.writeCustomers(null, customers);
        });
    }

    @Test
    void testWriteCustomer_WithNullPrintWriter() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            WriteCsvToResponse.writeCustomer(null, testCustomer);
        });
    }
}
