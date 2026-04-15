package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class PdfTest {

    private Pdf pdf;

    @BeforeEach
    void setUp() {
        pdf = new Pdf();
    }

    @Test
    void testPdfCreation() {
        // Arrange & Act
        Pdf newPdf = new Pdf();
        
        // Assert
        assertNotNull(newPdf);
    }

    @Test
    void testPdfBuilderPattern() {
        // Arrange & Act
        Pdf builtPdf = Pdf.builder()
                .id(1L)
                .name("Test PDF")
                .content("PDF Content")
                .build();
        
        // Assert
        assertNotNull(builtPdf);
        assertEquals(1L, builtPdf.getId());
        assertEquals("Test PDF", builtPdf.getName());
        assertEquals("PDF Content", builtPdf.getContent());
    }

    @Test
    void testAllArgsConstructor() {
        // Act
        Pdf pdf = new Pdf(1L, "Test PDF", "Content");
        
        // Assert
        assertNotNull(pdf);
        assertEquals(1L, pdf.getId());
        assertEquals("Test PDF", pdf.getName());
        assertEquals("Content", pdf.getContent());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 1L;
        
        // Act
        pdf.setId(expectedId);
        
        // Assert
        assertEquals(expectedId, pdf.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "Test PDF Document";
        
        // Act
        pdf.setName(expectedName);
        
        // Assert
        assertEquals(expectedName, pdf.getName());
    }

    @Test
    void testSetAndGetContent() {
        // Arrange
        String expectedContent = "This is PDF content";
        
        // Act
        pdf.setContent(expectedContent);
        
        // Assert
        assertEquals(expectedContent, pdf.getContent());
    }

    @Test
    void testPdfWithNullName() {
        // Arrange & Act
        pdf.setName(null);
        
        // Assert
        assertNull(pdf.getName());
    }

    @Test
    void testPdfWithEmptyName() {
        // Arrange & Act
        pdf.setName("");
        
        // Assert
        assertEquals("", pdf.getName());
    }

    @Test
    void testPdfWithNullContent() {
        // Arrange & Act
        pdf.setContent(null);
        
        // Assert
        assertNull(pdf.getContent());
    }

    @Test
    void testPdfWithEmptyContent() {
        // Arrange & Act
        pdf.setContent("");
        
        // Assert
        assertEquals("", pdf.getContent());
    }

    @Test
    void testPdfEquality() {
        // Arrange
        Pdf pdf1 = Pdf.builder()
                .id(1L)
                .name("Test")
                .content("Content")
                .build();
        
        Pdf pdf2 = Pdf.builder()
                .id(1L)
                .name("Test")
                .content("Content")
                .build();
        
        // Assert
        assertEquals(pdf1, pdf2);
    }

    @Test
    void testPdfToString() {
        // Arrange
        pdf.setId(1L);
        pdf.setName("Test PDF");
        pdf.setContent("Content");
        
        // Act
        String result = pdf.toString();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Test PDF"));
    }
}
