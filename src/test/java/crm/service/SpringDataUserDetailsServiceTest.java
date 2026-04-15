package crm.service;

import crm.entity.CurrentUser;
import crm.entity.Role;
import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpringDataUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SpringDataUserDetailsService userDetailsService;

    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setId(1);
        testRole.setName("ROLE_USER");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("test@test.com");
        testUser.setEnabled(1);
        testUser.setRole(testRole);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Arrange
        when(userService.findByUsername("testuser")).thenReturn(testUser);

        // Act
        UserDetails result = userDetailsService.loadUserByUsername("testuser");

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("password123", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(userService, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(userService.findByUsername("nonexistent")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent");
        });
        verify(userService, times(1)).findByUsername("nonexistent");
    }

    @Test
    void testLoadUserByUsername_ReturnsCurrentUser() {
        // Arrange
        when(userService.findByUsername("testuser")).thenReturn(testUser);

        // Act
        UserDetails result = userDetailsService.loadUserByUsername("testuser");

        // Assert
        assertInstanceOf(CurrentUser.class, result);
        CurrentUser currentUser = (CurrentUser) result;
        assertEquals(testUser, currentUser.getUser());
        verify(userService, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsername_WithAdminRole() {
        // Arrange
        testRole.setName("ROLE_ADMIN");
        when(userService.findByUsername("admin")).thenReturn(testUser);

        // Act
        UserDetails result = userDetailsService.loadUserByUsername("admin");

        // Assert
        assertNotNull(result);
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        verify(userService, times(1)).findByUsername("admin");
    }

    @Test
    void testLoadUserByUsername_WithManagerRole() {
        // Arrange
        testRole.setName("ROLE_MANAGER");
        when(userService.findByUsername("manager")).thenReturn(testUser);

        // Act
        UserDetails result = userDetailsService.loadUserByUsername("manager");

        // Assert
        assertNotNull(result);
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER")));
        verify(userService, times(1)).findByUsername("manager");
    }

    @Test
    void testLoadUserByUsername_NullUsername() {
        // Arrange
        when(userService.findByUsername(null)).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(null);
        });
    }

    @Test
    void testLoadUserByUsername_EmptyUsername() {
        // Arrange
        when(userService.findByUsername("")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("");
        });
    }
}
