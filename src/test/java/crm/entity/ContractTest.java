package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

class ContractTest {

    private Contract contract;

    @BeforeEach
    void setUp() {
        contract = new Contract();
    }

    @Test
    void testContractCreation() {
        // Arrange & Act
        Contract newContract = new Contract();
        
        // Assert
        assertNotNull(newContract);
    }

    @Test
    void testContractBuilderPattern() {
        // Arrange
        LocalDate beginDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        BigDecimal value = new BigDecimal("10000.00");
        
        // Act
        Contract builtContract = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .content("Contract Content")
                .value(value)
                .beginDate(beginDate)
                .endDate(endDate)
                .status(Status.PROPOSED)
                .build();
        
        // Assert
        assertNotNull(builtContract);
        assertEquals(1L, builtContract.getId());
        assertEquals("Test Contract", builtContract.getName());
        assertEquals(value, builtContract.getValue());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        LocalDate beginDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        BigDecimal value = new BigDecimal("5000.00");
        Customer customer = new Customer();
        User user = new User();
        
        // Act
        Contract contract = new Contract(1L, "Test", "Content", value, 
                                        beginDate, endDate, Status.PROPOSED, customer, user);
        
        // Assert
        assertNotNull(contract);
        assertEquals(1L, contract.getId());
        assertEquals("Test", contract.getName());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 1L;
        
        // Act
        contract.setId(expectedId);
        
        // Assert
        assertEquals(expectedId, contract.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "Test Contract";
        
        // Act
        contract.setName(expectedName);
        
        // Assert
        assertEquals(expectedName, contract.getName());
    }

    @Test
    void testSetAndGetContent() {
        // Arrange
        String expectedContent = "Contract content";
        
        // Act
        contract.setContent(expectedContent);
        
        // Assert
        assertEquals(expectedContent, contract.getContent());
    }

    @Test
    void testSetAndGetValue() {
        // Arrange
        BigDecimal expectedValue = new BigDecimal("15000.50");
        
        // Act
        contract.setValue(expectedValue);
        
        // Assert
        assertEquals(expectedValue, contract.getValue());
    }

    @Test
    void testSetAndGetBeginDate() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(2024, 1, 1);
        
        // Act
        contract.setBeginDate(expectedDate);
        
        // Assert
        assertEquals(expectedDate, contract.getBeginDate());
    }

    @Test
    void testSetAndGetEndDate() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(2024, 12, 31);
        
        // Act
        contract.setEndDate(expectedDate);
        
        // Assert
        assertEquals(expectedDate, contract.getEndDate());
    }

    @Test
    void testSetAndGetStatus() {
        // Arrange
        Status expectedStatus = Status.IMPLEMENTED;
        
        // Act
        contract.setStatus(expectedStatus);
        
        // Assert
        assertEquals(expectedStatus, contract.getStatus());
    }

    @Test
    void testSetAndGetCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");
        
        // Act
        contract.setCustomer(customer);
        
        // Assert
        assertNotNull(contract.getCustomer());
        assertEquals(1L, contract.getCustomer().getId());
    }

    @Test
    void testSetAndGetUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        
        // Act
        contract.setUser(user);
        
        // Assert
        assertNotNull(contract.getUser());
        assertEquals(1L, contract.getUser().getId());
    }

    @Test
    void testContractWithNullName() {
        // Arrange & Act
        contract.setName(null);
        
        // Assert
        assertNull(contract.getName());
    }

    @Test
    void testContractWithNullValue() {
        // Arrange & Act
        contract.setValue(null);
        
        // Assert
        assertNull(contract.getValue());
    }

    @Test
    void testContractWithZeroValue() {
        // Arrange
        BigDecimal zeroValue = BigDecimal.ZERO;
        
        // Act
        contract.setValue(zeroValue);
        
        // Assert
        assertEquals(BigDecimal.ZERO, contract.getValue());
    }

    @Test
    void testContractWithNegativeValue() {
        // Arrange
        BigDecimal negativeValue = new BigDecimal("-1000.00");
        
        // Act
        contract.setValue(negativeValue);
        
        // Assert
        assertEquals(negativeValue, contract.getValue());
    }

    @Test
    void testContractWithAllStatuses() {
        // Test PROPOSED
        contract.setStatus(Status.PROPOSED);
        assertEquals(Status.PROPOSED, contract.getStatus());
        
        // Test NEGOTIATED
        contract.setStatus(Status.NEGOTIATED);
        assertEquals(Status.NEGOTIATED, contract.getStatus());
        
        // Test IMPLEMENTED
        contract.setStatus(Status.IMPLEMENTED);
        assertEquals(Status.IMPLEMENTED, contract.getStatus());
        
        // Test DONE
        contract.setStatus(Status.DONE);
        assertEquals(Status.DONE, contract.getStatus());
    }

    @Test
    void testContractEquality() {
        // Arrange
        Contract contract1 = Contract.builder()
                .id(1L)
                .name("Test")
                .build();
        
        Contract contract2 = Contract.builder()
                .id(1L)
                .name("Test")
                .build();
        
        // Assert
        assertEquals(contract1, contract2);
    }

    @Test
    void testContractToString() {
        // Arrange
        contract.setId(1L);
        contract.setName("Test Contract");
        
        // Act
        String result = contract.toString();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Test Contract"));
    }
}
