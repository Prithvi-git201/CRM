package crm.repository;

import crm.entity.Pdf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PdfRepositoryTest {

    @Autowired
    private PdfRepository pdfRepository;

    @Test
    void testFindByName_Success() {
        // Arrange
        Pdf pdf = new Pdf();
        pdf.setName("Test PDF");
        pdf.setContent("Content");
        pdfRepository.save(pdf);

        // Act
        Pdf found = pdfRepository.findByName("Test PDF");

        // Assert
        assertNotNull(found);
        assertEquals("Test PDF", found.getName());
    }

    @Test
    void testFindByName_NotFound() {
        // Act
        Pdf found = pdfRepository.findByName("NONEXISTENT");

        // Assert
        assertNull(found);
    }

    @Test
    void testSavePdf() {
        // Arrange
        Pdf pdf = new Pdf();
        pdf.setName("New PDF");
        pdf.setContent("New Content");

        // Act
        Pdf saved = pdfRepository.save(pdf);

        // Assert
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("New PDF", saved.getName());
    }

    @Test
    void testFindAll() {
        // Arrange
        Pdf pdf1 = new Pdf();
        pdf1.setName("PDF1");
        pdfRepository.save(pdf1);

        Pdf pdf2 = new Pdf();
        pdf2.setName("PDF2");
        pdfRepository.save(pdf2);

        // Act
        Iterable<Pdf> pdfs = pdfRepository.findAll();

        // Assert
        assertNotNull(pdfs);
        assertTrue(pdfs.iterator().hasNext());
    }
}
