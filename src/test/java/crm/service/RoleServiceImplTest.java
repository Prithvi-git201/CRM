package crm.service;

import crm.entity.Role;
import crm.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setId(1);
        testRole.setName("ROLE_USER");
    }

    @Test
    void testListAllRoles() {
        // Arrange
        List<Role> roles = Arrays.asList(testRole);
        when(roleRepository.findAll()).thenReturn(roles);

        // Act
        Iterable<Role> result = roleService.listAllRoles();

        // Assert
        assertNotNull(result);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testListAllRoles_EmptyList() {
        // Arrange
        List<Role> emptyRoles = Arrays.asList();
        when(roleRepository.findAll()).thenReturn(emptyRoles);

        // Act
        Iterable<Role> result = roleService.listAllRoles();

        // Assert
        assertNotNull(result);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testListAllRoles_MultipleRoles() {
        // Arrange
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_USER");
        
        Role role2 = new Role();
        role2.setId(2);
        role2.setName("ROLE_ADMIN");
        
        List<Role> roles = Arrays.asList(role1, role2);
        when(roleRepository.findAll()).thenReturn(roles);

        // Act
        Iterable<Role> result = roleService.listAllRoles();

        // Assert
        assertNotNull(result);
        verify(roleRepository, times(1)).findAll();
    }
}
