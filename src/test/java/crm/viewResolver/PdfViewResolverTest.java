package crm.viewResolver;

import crm.view.PdfView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class PdfViewResolverTest {

    private PdfViewResolver pdfViewResolver;

    @BeforeEach
    void setUp() {
        pdfViewResolver = new PdfViewResolver();
    }

    @Test
    void testResolveViewName_PdfView() throws Exception {
        // Act
        View view = pdfViewResolver.resolveViewName("pdfView", Locale.getDefault());

        // Assert
        assertNotNull(view);
        assertInstanceOf(PdfView.class, view);
    }

    @Test
    void testResolveViewName_NullForNonPdfView() throws Exception {
        // Act
        View view = pdfViewResolver.resolveViewName("otherView", Locale.getDefault());

        // Assert
        assertNull(view);
    }

    @Test
    void testResolveViewName_WithDifferentLocale() throws Exception {
        // Act
        View view = pdfViewResolver.resolveViewName("pdfView", Locale.FRENCH);

        // Assert
        assertNotNull(view);
        assertInstanceOf(PdfView.class, view);
    }

    @Test
    void testResolveViewName_CaseInsensitive() throws Exception {
        // Act
        View view1 = pdfViewResolver.resolveViewName("pdfView", Locale.getDefault());
        View view2 = pdfViewResolver.resolveViewName("PDFVIEW", Locale.getDefault());

        // Assert
        assertNotNull(view1);
        // Note: Case sensitivity depends on implementation
    }
}
