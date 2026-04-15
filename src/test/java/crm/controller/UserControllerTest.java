package crm.controller;

import crm.entity.User;
import crm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private UserDetails userDetails;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private User testUser;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@test.com");
        testUser.setPassword("password");
        testUser.setEnabled(1);
    }

    @Test
    void testShowAllUsers() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(testUser);
        when(userService.listAllUsers()).thenReturn(Arrays.asList(testUser));

        // Act
        String viewName = userController.showAllUsers(model, userDetails);

        // Assert
        assertEquals("user/list", viewName);
        verify(model, times(1)).addAttribute(eq("currentUser"), any());
        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(userService, times(1)).findByUsername("testuser");
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testShowFormEditUser() {
        // Arrange
        when(userService.showUser(1L)).thenReturn(testUser);

        // Act
        String viewName = userController.showFormEditUser(model, 1L);

        // Assert
        assertEquals("user/edit", viewName);
        verify(model, times(1)).addAttribute("user", testUser);
        verify(userService, times(1)).showUser(1L);
    }

    @Test
    void testProcessRequestEditUser_Success() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(userService).editUser(any(User.class));

        // Act
        String viewName = userController.processRequestEditUser(1L, testUser, bindingResult);

        // Assert
        assertEquals("redirect:/user/list", viewName);
        verify(userService, times(1)).editUser(testUser);
    }

    @Test
    void testProcessRequestEditUser_WithErrors() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = userController.processRequestEditUser(1L, testUser, bindingResult);

        // Assert
        assertEquals("redirect:/user/edit/1", viewName);
        verify(userService, never()).editUser(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Arrange
        when(userService.showUser(1L)).thenReturn(testUser);
        doNothing().when(userService).deleteUser(any(User.class));

        // Act
        String viewName = userController.deleteUser(1L);

        // Assert
        assertEquals("redirect:/user/list", viewName);
        verify(userService, times(1)).showUser(1L);
        verify(userService, times(1)).deleteUser(testUser);
    }

    @Test
    void testDeleteUser_WithNonExistentId() {
        // Arrange
        when(userService.showUser(999L)).thenReturn(null);

        // Act
        String viewName = userController.deleteUser(999L);

        // Assert
        assertEquals("redirect:/user/list", viewName);
        verify(userService, times(1)).showUser(999L);
    }
}
