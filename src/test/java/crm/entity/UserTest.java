package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testUserCreation() {
        // Arrange & Act
        User newUser = new User();
        
        // Assert
        assertNotNull(newUser);
    }

    @Test
    void testUserBuilderPattern() {
        // Arrange & Act
        User builtUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .enabled(1)
                .build();
        
        // Assert
        assertNotNull(builtUser);
        assertEquals(1L, builtUser.getId());
        assertEquals("testuser", builtUser.getUsername());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");
        
        // Act
        User user = new User(1L, "testuser", "test@test.com", 
                            "John", "Doe", "password", 1, role);
        
        // Assert
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 1L;
        
        // Act
        user.setId(expectedId);
        
        // Assert
        assertEquals(expectedId, user.getId());
    }

    @Test
    void testSetAndGetUsername() {
        // Arrange
        String expectedUsername = "testuser";
        
        // Act
        user.setUsername(expectedUsername);
        
        // Assert
        assertEquals(expectedUsername, user.getUsername());
    }

    @Test
    void testSetAndGetEmail() {
        // Arrange
        String expectedEmail = "test@example.com";
        
        // Act
        user.setEmail(expectedEmail);
        
        // Assert
        assertEquals(expectedEmail, user.getEmail());
    }

    @Test
    void testSetAndGetFirstName() {
        // Arrange
        String expectedFirstName = "John";
        
        // Act
        user.setFirstName(expectedFirstName);
        
        // Assert
        assertEquals(expectedFirstName, user.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        // Arrange
        String expectedLastName = "Doe";
        
        // Act
        user.setLastName(expectedLastName);
        
        // Assert
        assertEquals(expectedLastName, user.getLastName());
    }

    @Test
    void testSetAndGetPassword() {
        // Arrange
        String expectedPassword = "password123";
        
        // Act
        user.setPassword(expectedPassword);
        
        // Assert
        assertEquals(expectedPassword, user.getPassword());
    }

    @Test
    void testSetAndGetEnabled() {
        // Arrange
        int expectedEnabled = 1;
        
        // Act
        user.setEnabled(expectedEnabled);
        
        // Assert
        assertEquals(expectedEnabled, user.getEnabled());
    }

    @Test
    void testSetAndGetRole() {
        // Arrange
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");
        
        // Act
        user.setRole(role);
        
        // Assert
        assertNotNull(user.getRole());
        assertEquals(1, user.getRole().getId());
        assertEquals("ROLE_USER", user.getRole().getName());
    }

    @Test
    void testGetColumnCount() {
        // Act
        int columnCount = user.getColumnCount();
        
        // Assert
        assertTrue(columnCount > 0);
    }

    @Test
    void testGetRoleId() {
        // Arrange
        Role role = new Role();
        role.setId(5);
        role.setName("ROLE_ADMIN");
        user.setRole(role);
        
        // Act
        int roleId = user.getRole_id();
        
        // Assert
        assertEquals(5, roleId);
    }

    @Test
    void testGetRoleName() {
        // Arrange
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_MANAGER");
        user.setRole(role);
        
        // Act
        String roleName = user.getRole_name();
        
        // Assert
        assertEquals("ROLE_MANAGER", roleName);
    }

    @Test
    void testGetName() {
        // Arrange
        user.setFirstName("John");
        user.setLastName("Doe");
        
        // Act
        String fullName = user.getName();
        
        // Assert
        assertEquals("John Doe", fullName);
    }

    @Test
    void testGetNameWithNullValues() {
        // Arrange
        user.setFirstName(null);
        user.setLastName(null);
        
        // Act
        String fullName = user.getName();
        
        // Assert
        assertEquals("null null", fullName);
    }

    @Test
    void testUserWithNullEmail() {
        // Arrange & Act
        user.setEmail(null);
        
        // Assert
        assertNull(user.getEmail());
    }

    @Test
    void testUserWithEmptyUsername() {
        // Arrange & Act
        user.setUsername("");
        
        // Assert
        assertEquals("", user.getUsername());
    }

    @Test
    void testUserEnabledFlag() {
        // Arrange & Act
        user.setEnabled(0);
        
        // Assert
        assertEquals(0, user.getEnabled());
    }

    @Test
    void testUserEquality() {
        // Arrange
        User user1 = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@test.com")
                .build();
        
        User user2 = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@test.com")
                .build();
        
        // Assert
        assertEquals(user1, user2);
    }

    @Test
    void testUserToString() {
        // Arrange
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@test.com");
        
        // Act
        String result = user.toString();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("testuser"));
    }
}
