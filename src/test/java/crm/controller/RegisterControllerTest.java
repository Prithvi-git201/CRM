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
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private RegisterController registerController;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@test.com");
        testUser.setPassword("password");
    }

    @Test
    void testShowFormRegister() {
        // Act
        String viewName = registerController.showFormRegister(model);

        // Assert
        assertEquals("register", viewName);
        verify(model, times(1)).addAttribute(eq("user"), any(User.class));
    }

    @Test
    void testProcessRequestRegister_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(userService).saveUser(any(User.class));

        // Act
        String viewName = registerController.processRequestRegister(testUser, bindingResult);

        // Assert
        assertEquals("redirect:/", viewName);
        verify(userService, times(1)).saveUser(testUser);
    }

    @Test
    void testProcessRequestRegister_WithErrors() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = registerController.processRequestRegister(testUser, bindingResult);

        // Assert
        assertEquals("redirect:/register", viewName);
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    void testProcessRequestRegister_WithNullUser() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(userService).saveUser(any(User.class));

        // Act
        String viewName = registerController.processRequestRegister(null, bindingResult);

        // Assert
        assertEquals("redirect:/", viewName);
    }
}
