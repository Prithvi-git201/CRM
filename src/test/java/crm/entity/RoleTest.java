package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
    }

    @Test
    void testRoleCreation() {
        // Arrange & Act
        Role newRole = new Role();
        
        // Assert
        assertNotNull(newRole);
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        int expectedId = 1;
        
        // Act
        role.setId(expectedId);
        
        // Assert
        assertEquals(expectedId, role.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "ROLE_ADMIN";
        
        // Act
        role.setName(expectedName);
        
        // Assert
        assertEquals(expectedName, role.getName());
    }

    @Test
    void testRoleWithNullName() {
        // Arrange & Act
        role.setName(null);
        
        // Assert
        assertNull(role.getName());
    }

    @Test
    void testRoleWithEmptyName() {
        // Arrange
        String emptyName = "";
        
        // Act
        role.setName(emptyName);
        
        // Assert
        assertEquals(emptyName, role.getName());
    }

    @Test
    void testRoleEquality() {
        // Arrange
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_USER");
        
        Role role2 = new Role();
        role2.setId(1);
        role2.setName("ROLE_USER");
        
        // Assert
        assertEquals(role1, role2);
    }

    @Test
    void testRoleHashCode() {
        // Arrange
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_USER");
        
        Role role2 = new Role();
        role2.setId(1);
        role2.setName("ROLE_USER");
        
        // Assert
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    void testRoleToString() {
        // Arrange
        role.setId(1);
        role.setName("ROLE_ADMIN");
        
        // Act
        String result = role.toString();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("ROLE_ADMIN"));
    }
}
