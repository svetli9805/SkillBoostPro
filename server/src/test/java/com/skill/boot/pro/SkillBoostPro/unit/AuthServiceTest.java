package com.skill.boot.pro.SkillBoostPro.unit;

import com.skill.boot.pro.SkillBoostPro.entity.Role;
import com.skill.boot.pro.SkillBoostPro.entity.User;
import com.skill.boot.pro.SkillBoostPro.exception.ApiException;
import com.skill.boot.pro.SkillBoostPro.payload.LoginRequest;
import com.skill.boot.pro.SkillBoostPro.payload.RegisterRequest;
import com.skill.boot.pro.SkillBoostPro.repository.RoleRepository;
import com.skill.boot.pro.SkillBoostPro.repository.UserRepository;
import com.skill.boot.pro.SkillBoostPro.security.JwtTokenProvider;
import com.skill.boot.pro.SkillBoostPro.service.AuthService;
import com.skill.boot.pro.SkillBoostPro.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthService authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthServiceImpl(authenticationManager, userRepository, roleRepository, passwordEncoder, jwtTokenProvider);
    }

    @Test
    public void testRegisterSuccess() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testUser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("testPassword");

        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName("STUDENT");
        when(roleRepository.findByName("STUDENT")).thenReturn(Optional.of(userRole));

        User savedUser = new User();
        when(userRepository.save(any())).thenReturn(savedUser);

        // Act
        String result = authService.register(registerRequest);

        // Assert
        assertNotNull(result);
        assertEquals("User registered successfully!.", result);

        verify(userRepository, times(1)).existsByUsername("testUser");
        verify(userRepository, times(1)).existsByEmail("test@example.com");
        verify(roleRepository, times(1)).findByName("STUDENT");
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testRegisterUsernameExists() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existingUser");
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        // Act & Assert
        assertThrows(ApiException.class, () -> authService.register(registerRequest));
        verify(userRepository, times(1)).existsByUsername("existingUser");
        verify(userRepository, never()).existsByEmail(any());
        verify(roleRepository, never()).findByName(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testLoginSuccess() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("testUser");
        loginRequest.setPassword("testPassword");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("generatedToken");

        // Act
        String result = authService.login(loginRequest);

        // Assert
        assertEquals("generatedToken", result);
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtTokenProvider, times(1)).generateToken(authentication);
    }

    @Test
    public void testLoginFailure() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("nonexistentUser");
        loginRequest.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new RuntimeException("Authentication failed"));

        // Act & Assert
        try {
            authService.login(loginRequest);
        } catch (RuntimeException e) {
            assertEquals("Authentication failed", e.getMessage());
        }

        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtTokenProvider, never()).generateToken(any());
    }
}