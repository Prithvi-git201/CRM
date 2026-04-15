package crm;

import crm.viewResolver.CsvViewResolver;
import crm.viewResolver.ExcelViewResolver;
import crm.viewResolver.PdfViewResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebAppConfigTest {

    private WebAppConfig webAppConfig;

    @BeforeEach
    void setUp() {
        webAppConfig = new WebAppConfig();
    }

    @Test
    void testAddViewControllers() {
        // Arrange
        ViewControllerRegistry registry = mock(ViewControllerRegistry.class);

        // Act
        webAppConfig.addViewControllers(registry);

        // Assert
        verify(registry, atLeastOnce()).addViewController(anyString());
    }

    @Test
    void testConfigureContentNegotiation() {
        // Arrange
        ContentNegotiationConfigurer configurer = mock(ContentNegotiationConfigurer.class);

        // Act
        webAppConfig.configureContentNegotiation(configurer);

        // Assert
        verify(configurer, times(1)).ignoreAcceptHeader(false);
        verify(configurer, times(1)).defaultContentType(MediaType.APPLICATION_JSON);
        verify(configurer, times(1)).mediaTypes(anyMap());
    }

    @Test
    void testTemplateResolver() {
        // Act
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();

        // Assert
        assertNotNull(resolver);
        assertEquals("templates/", resolver.getPrefix());
        assertEquals(".html", resolver.getSuffix());
        assertEquals("HTML", resolver.getTemplateMode());
        assertEquals("UTF-8", resolver.getCharacterEncoding());
    }

    @Test
    void testTemplateEngine() {
        // Act
        SpringTemplateEngine engine = webAppConfig.templateEngine();

        // Assert
        assertNotNull(engine);
        assertNotNull(engine.getTemplateResolvers());
    }

    @Test
    void testViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.viewResolver();

        // Assert
        assertNotNull(resolver);
        assertInstanceOf(ThymeleafViewResolver.class, resolver);
    }

    @Test
    void testExcelViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.excelViewResolver();

        // Assert
        assertNotNull(resolver);
        assertInstanceOf(ExcelViewResolver.class, resolver);
    }

    @Test
    void testCsvViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.csvViewResolver();

        // Assert
        assertNotNull(resolver);
        assertInstanceOf(CsvViewResolver.class, resolver);
    }

    @Test
    void testPdfViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.pdfViewResolver();

        // Assert
        assertNotNull(resolver);
        assertInstanceOf(PdfViewResolver.class, resolver);
    }

    @Test
    void testContentNegotiatingViewResolver() {
        // Arrange
        ContentNegotiationManager manager = mock(ContentNegotiationManager.class);

        // Act
        ViewResolver resolver = webAppConfig.contentNegotiatingViewResolver(manager);

        // Assert
        assertNotNull(resolver);
    }
}
