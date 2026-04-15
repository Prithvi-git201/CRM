package crm.controller;

import crm.entity.Pdf;
import crm.service.PdfService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PdfControllerTest {

    @Mock
    private PdfService pdfService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private PdfController pdfController;

    private Pdf testPdf;

    @BeforeEach
    void setUp() {
        testPdf = new Pdf();
        testPdf.setId(1L);
        testPdf.setName("Test PDF");
        testPdf.setContent("PDF Content");
    }

    @Test
    void testShowFormAddPdf() {
        // Act
        String viewName = pdfController.showFormAddPdf(model);

        // Assert
        assertEquals("pdf/add", viewName);
        verify(model, times(1)).addAttribute(eq("pdf"), any(Pdf.class));
    }

    @Test
    void testProcessRequestAddPdf_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(pdfService).savePdf(any(Pdf.class));

        // Act
        String viewName = pdfController.processRequestAddPdf(testPdf, bindingResult);

        // Assert
        assertEquals("pdf/success", viewName);
        verify(pdfService, times(1)).savePdf(testPdf);
    }

    @Test
    void testProcessRequestAddPdf_WithErrors() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = pdfController.processRequestAddPdf(testPdf, bindingResult);

        // Assert
        assertEquals("redirect:/pdf/add", viewName);
        verify(pdfService, never()).savePdf(any(Pdf.class));
    }

    @Test
    void testShowFormSearchPdf() {
        // Act
        String viewName = pdfController.showFormSearchPdf(model);

        // Assert
        assertEquals("pdf/search", viewName);
        verify(model, times(1)).addAttribute(eq("pdf"), any(Pdf.class));
    }

    @Test
    void testProcessRequestSearchPdf() {
        // Arrange
        when(pdfService.findByName("Test PDF")).thenReturn(testPdf);

        // Act
        String viewName = pdfController.processRequestSearchPdf(testPdf, model);

        // Assert
        assertEquals("pdf/show", viewName);
        verify(model, times(1)).addAttribute(eq("pdf"), any());
        verify(pdfService, times(1)).findByName(anyString());
    }

    @Test
    void testProcessRequestSearchPdf_NotFound() {
        // Arrange
        when(pdfService.findByName("Nonexistent")).thenReturn(null);
        testPdf.setName("Nonexistent");

        // Act
        String viewName = pdfController.processRequestSearchPdf(testPdf, model);

        // Assert
        assertEquals("pdf/show", viewName);
        verify(pdfService, times(1)).findByName("Nonexistent");
    }
}
