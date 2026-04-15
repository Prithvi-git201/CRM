package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

class CurrentUserTest {

    private CurrentUser currentUser;
    private User user;

    @BeforeEach
    void setUp() {
        currentUser = new CurrentUser();
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
    }

    @Test
    void testCurrentUserCreation() {
        // Arrange & Act
        CurrentUser newCurrentUser = new CurrentUser();
        
        // Assert
        assertNotNull(newCurrentUser);
    }

    @Test
    void testSetAndGetUser() {
        // Act
        currentUser.setUser(user);
        
        // Assert
        assertNotNull(currentUser.getUser());
        assertEquals("testuser", currentUser.getUser().getUsername());
    }

    @Test
    void testSetAndGetAuthorities() {
        // Arrange
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        // Act
        currentUser.setAuthorities(authorities);
        
        // Assert
        assertNotNull(currentUser.getAuthorities());
        assertEquals(1, currentUser.getAuthorities().size());
    }

    @Test
    void testGetAuthorities() {
        // Arrange
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        currentUser.setAuthorities(authorities);
        
        // Act
        var result = currentUser.getAuthorities();
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testGetPassword() {
        // Arrange
        user.setPassword("testpassword");
        currentUser.setUser(user);
        
        // Act
        String password = currentUser.getPassword();
        
        // Assert
        assertEquals("testpassword", password);
    }

    @Test
    void testGetUsername() {
        // Arrange
        user.setUsername("johndoe");
        currentUser.setUser(user);
        
        // Act
        String username = currentUser.getUsername();
        
        // Assert
        assertEquals("johndoe", username);
    }

    @Test
    void testIsAccountNonExpired() {
        // Act
        boolean result = currentUser.isAccountNonExpired();
        
        // Assert
        assertTrue(result);
    }

    @Test
    void testIsAccountNonLocked() {
        // Act
        boolean result = currentUser.isAccountNonLocked();
        
        // Assert
        assertTrue(result);
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Act
        boolean result = currentUser.isCredentialsNonExpired();
        
        // Assert
        assertTrue(result);
    }

    @Test
    void testIsEnabled() {
        // Act
        boolean result = currentUser.isEnabled();
        
        // Assert
        assertTrue(result);
    }

    @Test
    void testCurrentUserWithMultipleAuthorities() {
        // Arrange
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        currentUser.setAuthorities(authorities);
        
        // Act
        var result = currentUser.getAuthorities();
        
        // Assert
        assertEquals(3, result.size());
    }

    @Test
    void testCurrentUserWithNullUser() {
        // Arrange
        currentUser.setUser(null);
        
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            currentUser.getUsername();
        });
    }

    @Test
    void testCurrentUserWithEmptyAuthorities() {
        // Arrange
        Set<GrantedAuthority> authorities = new HashSet<>();
        currentUser.setAuthorities(authorities);
        
        // Act
        var result = currentUser.getAuthorities();
        
        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testCurrentUserEquality() {
        // Arrange
        CurrentUser user1 = new CurrentUser();
        user1.setUser(user);
        
        CurrentUser user2 = new CurrentUser();
        user2.setUser(user);
        
        // Assert
        assertEquals(user1, user2);
    }

    @Test
    void testCurrentUserToString() {
        // Arrange
        currentUser.setUser(user);
        
        // Act
        String result = currentUser.toString();
        
        // Assert
        assertNotNull(result);
    }
}
