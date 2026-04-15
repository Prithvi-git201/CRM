package crm;

import crm.service.SpringDataUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @Test
    void testPasswordEncoder() {
        // Arrange
        securityConfig = new SecurityConfig();

        // Act
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();

        // Assert
        assertNotNull(encoder);
        assertInstanceOf(BCryptPasswordEncoder.class, encoder);
    }

    @Test
    void testCustomUserDetailsService() {
        // Arrange
        securityConfig = new SecurityConfig();

        // Act
        SpringDataUserDetailsService service = securityConfig.customUserDetailsService();

        // Assert
        assertNotNull(service);
        assertInstanceOf(SpringDataUserDetailsService.class, service);
    }

    @Test
    void testAuthenticationManager() throws Exception {
        // Arrange
        securityConfig = new SecurityConfig();
        AuthenticationConfiguration authConfig = mock(AuthenticationConfiguration.class);
        AuthenticationManager mockManager = mock(AuthenticationManager.class);
        when(authConfig.getAuthenticationManager()).thenReturn(mockManager);

        // Act
        AuthenticationManager manager = securityConfig.authenticationManager(authConfig);

        // Assert
        assertNotNull(manager);
        verify(authConfig, times(1)).getAuthenticationManager();
    }

    @Test
    void testPasswordEncoderEncryption() {
        // Arrange
        securityConfig = new SecurityConfig();
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String rawPassword = "testPassword123";

        // Act
        String encodedPassword = encoder.encode(rawPassword);

        // Assert
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(encoder.matches(rawPassword, encodedPassword));
    }

    @Test
    void testPasswordEncoderDifferentPasswords() {
        // Arrange
        securityConfig = new SecurityConfig();
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String password1 = "password1";
        String password2 = "password2";

        // Act
        String encoded1 = encoder.encode(password1);
        String encoded2 = encoder.encode(password2);

        // Assert
        assertNotEquals(encoded1, encoded2);
        assertTrue(encoder.matches(password1, encoded1));
        assertFalse(encoder.matches(password1, encoded2));
    }
}
