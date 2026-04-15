package crm.service;

import crm.entity.Pdf;
import crm.repository.PdfRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PdfServiceImplTest {

    @Mock
    private PdfRepository pdfRepository;

    @InjectMocks
    private PdfServiceImpl pdfService;

    private Pdf testPdf;

    @BeforeEach
    void setUp() {
        testPdf = new Pdf();
        testPdf.setId(1L);
        testPdf.setName("Test PDF");
        testPdf.setContent("PDF Content");
    }

    @Test
    void testFindByName_Success() {
        // Arrange
        when(pdfRepository.findByName("Test PDF")).thenReturn(testPdf);

        // Act
        Pdf result = pdfService.findByName("Test PDF");

        // Assert
        assertNotNull(result);
        assertEquals("Test PDF", result.getName());
        verify(pdfRepository, times(1)).findByName("Test PDF");
    }

    @Test
    void testFindByName_NotFound() {
        // Arrange
        when(pdfRepository.findByName("Nonexistent")).thenReturn(null);

        // Act
        Pdf result = pdfService.findByName("Nonexistent");

        // Assert
        assertNull(result);
        verify(pdfRepository, times(1)).findByName("Nonexistent");
    }

    @Test
    void testSavePdf() {
        // Arrange
        when(pdfRepository.save(any(Pdf.class))).thenReturn(testPdf);

        // Act
        pdfService.savePdf(testPdf);

        // Assert
        verify(pdfRepository, times(1)).save(testPdf);
    }

    @Test
    void testSavePdf_WithNullContent() {
        // Arrange
        testPdf.setContent(null);
        when(pdfRepository.save(any(Pdf.class))).thenReturn(testPdf);

        // Act
        pdfService.savePdf(testPdf);

        // Assert
        verify(pdfRepository, times(1)).save(testPdf);
    }

    @Test
    void testSavePdf_WithEmptyName() {
        // Arrange
        testPdf.setName("");
        when(pdfRepository.save(any(Pdf.class))).thenReturn(testPdf);

        // Act
        pdfService.savePdf(testPdf);

        // Assert
        verify(pdfRepository, times(1)).save(testPdf);
    }
}
