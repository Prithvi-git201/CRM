package crm.service;

import crm.entity.Role;
import crm.entity.User;
import crm.repository.RoleRepository;
import crm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SpringDataUserDetailsService springDataUserDetailsService;

    @InjectMocks
    private UserServiceImpl userService;

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
        testUser.setEmail("test@test.com");
        testUser.setPassword("password");
        testUser.setEnabled(1);
        testUser.setRole(testRole);
    }

    @Test
    void testFindByUsername_Success() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);

        // Act
        User result = userService.findByUsername("testuser");

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testFindByUsername_NotFound() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        // Act
        User result = userService.findByUsername("nonexistent");

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }

    @Test
    void testListAllUsers() {
        // Arrange
        Iterable<User> users = Arrays.asList(testUser);
        when(userRepository.findAllByEnabled(1)).thenReturn(users);

        // Act
        Iterable<User> result = userService.listAllUsers();

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findAllByEnabled(1);
    }

    @Test
    void testShowUser_Found() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        User result = userService.showUser(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testShowUser_NotFound() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        User result = userService.showUser(999L);

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void testEditUser() {
        // Arrange
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(testRole));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.editUser(testUser);

        // Assert
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testEditUser_WithNullRole() {
        // Arrange
        testUser.setRole(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(testRole);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.editUser(testUser);

        // Assert
        verify(roleRepository, times(1)).findByName("ROLE_USER");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.deleteUser(testUser);

        // Assert
        assertEquals(0, testUser.getEnabled());
        assertNull(testUser.getPassword());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testDeleteUser_VerifyDisabled() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.deleteUser(testUser);

        // Assert
        assertEquals(0, testUser.getEnabled());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser_VerifyPasswordNull() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.deleteUser(testUser);

        // Assert
        assertNull(testUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
