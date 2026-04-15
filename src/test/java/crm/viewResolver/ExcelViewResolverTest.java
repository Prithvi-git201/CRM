package crm.viewResolver;

import crm.view.ExcelView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ExcelViewResolverTest {

    private ExcelViewResolver excelViewResolver;

    @BeforeEach
    void setUp() {
        excelViewResolver = new ExcelViewResolver();
    }

    @Test
    void testResolveViewName_ExcelView() throws Exception {
        // Act
        View view = excelViewResolver.resolveViewName("excelView", Locale.getDefault());

        // Assert
        assertNotNull(view);
        assertInstanceOf(ExcelView.class, view);
    }

    @Test
    void testResolveViewName_NullForNonExcelView() throws Exception {
        // Act
        View view = excelViewResolver.resolveViewName("otherView", Locale.getDefault());

        // Assert
        assertNull(view);
    }

    @Test
    void testResolveViewName_WithDifferentLocale() throws Exception {
        // Act
        View view = excelViewResolver.resolveViewName("excelView", Locale.GERMAN);

        // Assert
        assertNotNull(view);
        assertInstanceOf(ExcelView.class, view);
    }
}
