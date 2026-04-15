package crm.repository;

import crm.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByName_Success() {
        // Arrange
        Role role = new Role();
        role.setName("ROLE_TEST");
        roleRepository.save(role);

        // Act
        Role found = roleRepository.findByName("ROLE_TEST");

        // Assert
        assertNotNull(found);
        assertEquals("ROLE_TEST", found.getName());
    }

    @Test
    void testFindByName_NotFound() {
        // Act
        Role found = roleRepository.findByName("NONEXISTENT");

        // Assert
        assertNull(found);
    }

    @Test
    void testSaveRole() {
        // Arrange
        Role role = new Role();
        role.setName("ROLE_NEW");

        // Act
        Role saved = roleRepository.save(role);

        // Assert
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("ROLE_NEW", saved.getName());
    }

    @Test
    void testFindAll() {
        // Arrange
        Role role1 = new Role();
        role1.setName("ROLE_1");
        roleRepository.save(role1);

        Role role2 = new Role();
        role2.setName("ROLE_2");
        roleRepository.save(role2);

        // Act
        Iterable<Role> roles = roleRepository.findAll();

        // Assert
        assertNotNull(roles);
        assertTrue(roles.iterator().hasNext());
    }
}
