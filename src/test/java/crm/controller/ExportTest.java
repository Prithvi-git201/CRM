package crm.controller;

import crm.entity.User;
import crm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExportTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private Export export;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@test.com");
    }

    @Test
    void testExportToPdf() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(Arrays.asList(testUser));

        // Act
        String viewName = export.exportToPdf(model);

        // Assert
        assertEquals("pdfView", viewName);
        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testExportToExcel() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(Arrays.asList(testUser));

        // Act
        String viewName = export.exportToExcel(model);

        // Assert
        assertEquals("excelView", viewName);
        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testExportToCsv() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(Arrays.asList(testUser));

        // Act
        String viewName = export.exportToCsv(model);

        // Assert
        assertEquals("csvView", viewName);
        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testExportToPdf_EmptyUserList() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        // Act
        String viewName = export.exportToPdf(model);

        // Assert
        assertEquals("pdfView", viewName);
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testExportToExcel_EmptyUserList() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        // Act
        String viewName = export.exportToExcel(model);

        // Assert
        assertEquals("excelView", viewName);
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testExportToCsv_EmptyUserList() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        // Act
        String viewName = export.exportToCsv(model);

        // Assert
        assertEquals("csvView", viewName);
        verify(userService, times(1)).listAllUsers();
    }
}
