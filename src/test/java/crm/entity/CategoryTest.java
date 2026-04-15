package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void testCategoryCreation() {
        // Arrange & Act
        Category newCategory = new Category();
        
        // Assert
        assertNotNull(newCategory);
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 1L;
        
        // Act
        category.setId(expectedId);
        
        // Assert
        assertEquals(expectedId, category.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "Premium";
        
        // Act
        category.setName(expectedName);
        
        // Assert
        assertEquals(expectedName, category.getName());
    }

    @Test
    void testCategoryWithNullName() {
        // Arrange & Act
        category.setName(null);
        
        // Assert
        assertNull(category.getName());
    }

    @Test
    void testCategoryWithEmptyName() {
        // Arrange
        String emptyName = "";
        
        // Act
        category.setName(emptyName);
        
        // Assert
        assertEquals(emptyName, category.getName());
    }

    @Test
    void testCategoryEquality() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Premium");
        
        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Premium");
        
        // Assert
        assertEquals(category1, category2);
    }

    @Test
    void testCategoryHashCode() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Premium");
        
        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Premium");
        
        // Assert
        assertEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    void testCategoryToString() {
        // Arrange
        category.setId(1L);
        category.setName("Premium");
        
        // Act
        String result = category.toString();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Premium"));
    }
}
