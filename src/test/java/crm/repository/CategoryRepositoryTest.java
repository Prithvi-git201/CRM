package crm.repository;

import crm.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testFindByName_Success() {
        // Arrange
        Category category = new Category();
        category.setName("Premium");
        categoryRepository.save(category);

        // Act
        Category found = categoryRepository.findByName("Premium");

        // Assert
        assertNotNull(found);
        assertEquals("Premium", found.getName());
    }

    @Test
    void testFindByName_NotFound() {
        // Act
        Category found = categoryRepository.findByName("NONEXISTENT");

        // Assert
        assertNull(found);
    }

    @Test
    void testSaveCategory() {
        // Arrange
        Category category = new Category();
        category.setName("Standard");

        // Act
        Category saved = categoryRepository.save(category);

        // Assert
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Standard", saved.getName());
    }

    @Test
    void testFindAll() {
        // Arrange
        Category category1 = new Category();
        category1.setName("Category1");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Category2");
        categoryRepository.save(category2);

        // Act
        Iterable<Category> categories = categoryRepository.findAll();

        // Assert
        assertNotNull(categories);
        assertTrue(categories.iterator().hasNext());
    }
}
