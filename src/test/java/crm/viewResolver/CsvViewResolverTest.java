package crm.viewResolver;

import crm.view.CsvView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CsvViewResolverTest {

    private CsvViewResolver csvViewResolver;

    @BeforeEach
    void setUp() {
        csvViewResolver = new CsvViewResolver();
    }

    @Test
    void testResolveViewName_CsvView() throws Exception {
        // Act
        View view = csvViewResolver.resolveViewName("csvView", Locale.getDefault());

        // Assert
        assertNotNull(view);
        assertInstanceOf(CsvView.class, view);
    }

    @Test
    void testResolveViewName_NullForNonCsvView() throws Exception {
        // Act
        View view = csvViewResolver.resolveViewName("otherView", Locale.getDefault());

        // Assert
        assertNull(view);
    }

    @Test
    void testResolveViewName_WithDifferentLocale() throws Exception {
        // Act
        View view = csvViewResolver.resolveViewName("csvView", Locale.JAPANESE);

        // Assert
        assertNotNull(view);
        assertInstanceOf(CsvView.class, view);
    }
}
