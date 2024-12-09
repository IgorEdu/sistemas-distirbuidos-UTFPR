package com.UTFPR.server.service;

import com.UTFPR.domain.contracts.CredentialProvider;
import com.UTFPR.domain.dto.CadastroDTO;
import com.UTFPR.domain.entities.User;
import com.UTFPR.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testIsValidRa_ValidRa() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getRa()).thenReturn("1234567");

        boolean result = userService.isValidRa(credentialProvider);

        assertTrue(result, "Expected RA to be valid");
    }

    @Test
    void testIsValidRa_ContainsLetters() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getRa()).thenReturn("abc123");

        boolean result = userService.isValidRa(credentialProvider);

        assertFalse(result, "Expected RA to be invalid");
    }

    @Test
    void testIsValidRa_InvalidWhenRaIsNull() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getRa()).thenReturn(null);

        boolean result = userService.isValidRa(credentialProvider);

        assertFalse(result, "Expected RA to be invalid");
    }

    @Test
    void testIsValidRa_BlankRa() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getRa()).thenReturn("");

        boolean result = userService.isValidRa(credentialProvider);

        assertFalse(result, "Expected RA to be invalid");
    }

    @Test
    void testIsValidRa_TooShort() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getRa()).thenReturn("123456");

        boolean result = userService.isValidRa(credentialProvider);

        assertFalse(result, "Expected RA to be invalid");
    }

    @Test
    void testIsValidRa_TooLong() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getRa()).thenReturn("12345678");

        boolean result = userService.isValidRa(credentialProvider);

        assertFalse(result, "Expected RA to be invalid");
    }

    @Test
    void testIsValidPassword_ValidPassword() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getSenha()).thenReturn("passwordABC");

        boolean result = userService.isValidPassword(credentialProvider);

        assertTrue(result, "Expected password to be valid");
    }

    @Test
    void testIsValidPassword_InvalidWhenContainsNumbers() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getSenha()).thenReturn("password123");

        boolean result = userService.isValidPassword(credentialProvider);

        assertFalse(result, "Expected password to be invalid");
    }

    @Test
    void testIsValidPassword_InvalidWhenContainsSpecialCharacters() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getSenha()).thenReturn("password!@#");

        boolean result = userService.isValidPassword(credentialProvider);

        assertFalse(result, "Expected password to be invalid");
    }

    @Test
    void testIsValidPassword_InvalidWhenPasswordTooShort() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getSenha()).thenReturn("short12");

        boolean result = userService.isValidPassword(credentialProvider);

        assertFalse(result, "Expected password to be invalid");
    }

    @Test
    void testIsValidPassword_InvalidWhenPasswordTooLong() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getSenha()).thenReturn("passwordtoolong123456");

        boolean result = userService.isValidPassword(credentialProvider);

        assertFalse(result, "Expected password to be invalid");
    }

    @Test
    void testIsValidPassword_InvalidWhenPasswordIsNull() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getSenha()).thenReturn(null);

        boolean result = userService.isValidPassword(credentialProvider);

        assertFalse(result, "Expected password to be invalid");
    }

    @Test
    void testIsValidName_ValidName() {
        CadastroDTO cadastroDTO = mock(CadastroDTO.class);
        when(cadastroDTO.getNome()).thenReturn("VALID NAME");

        boolean result = userService.isValidName(cadastroDTO);

        assertTrue(result, "Expected name to be valid");
    }

    @Test
    void testIsValidName_InvalidWhenNameIsNull() {
        CadastroDTO cadastroDTO = mock(CadastroDTO.class);
        when(cadastroDTO.getNome()).thenReturn(null);

        boolean result = userService.isValidName(cadastroDTO);

        assertFalse(result, "Expected name to be invalid");
    }

    @Test
    void testIsValidName_InvalidWhenNameTooLong() {
        CadastroDTO cadastroDTO = mock(CadastroDTO.class);
        when(cadastroDTO.getNome()).thenReturn("INVALID NAME IS TOO LONG FOR REGISTER INVALID NAME ");

        boolean result = userService.isValidName(cadastroDTO);

        assertFalse(result, "Expected name to be invalid");
    }

    @Test
    void testIsValidName_InvalidWhenNameContainsSpecialCharacters() {
        CadastroDTO cadastroDTO = mock(CadastroDTO.class);
        when(cadastroDTO.getNome()).thenReturn("INVALID NAME $$");

        boolean result = userService.isValidName(cadastroDTO);

        assertFalse(result, "Expected name to be invalid");
    }

    @Test
    void testIsValidName_InvalidWhenNameHasLowercaseLetters() {
        CadastroDTO cadastroDTO = mock(CadastroDTO.class);
        when(cadastroDTO.getNome()).thenReturn("Invalid Name");

        boolean result = userService.isValidName(cadastroDTO);

        assertFalse(result, "Expected name to be invalid");
    }

    @Test
    void testIsExistentUser_UserExistsAndPasswordMatches() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getRa()).thenReturn("1234567");
        when(credentialProvider.getSenha()).thenReturn("password123");

        User user = mock(User.class);
        when(user.isSenha("password123")).thenReturn(true);

        when(userRepository.findUserByRa("1234567")).thenReturn(List.of(user));

        boolean result = userService.isExistentUser(credentialProvider);

        assertTrue(result, "Expected the user to exist with matching password");
    }

    @Test
    void testIsExistentUser_UserExistsButPasswordDoesNotMatch() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getRa()).thenReturn("1234567");
        when(credentialProvider.getSenha()).thenReturn("wrongPassword");

        User user = mock(User.class);
        when(user.isSenha("wrongPassword")).thenReturn(false);

        when(userRepository.findUserByRa("1234567")).thenReturn(List.of(user));

        boolean result = userService.isExistentUser(credentialProvider);

        assertFalse(result, "Expected the user to exist but with a mismatched password");
    }

    @Test
    void testIsExistentUser_UserDoesNotExist() {
        CredentialProvider credentialProvider = mock(CredentialProvider.class);
        when(credentialProvider.getRa()).thenReturn("1234567");

        when(userRepository.findUserByRa("1234567")).thenReturn(List.of());

        boolean result = userService.isExistentUser(credentialProvider);

        assertFalse(result, "Expected no user to exist");
    }

//    @Test
//    void testCanRegisterUser_UserCanBeRegistered() {
//        CadastroDTO cadastroDTO = mock(CadastroDTO.class);
//        when(cadastroDTO.getRa()).thenReturn("1234567");
//        when(cadastroDTO.getSenha()).thenReturn("validPassword");
//
//        when(userRepository.findUserByRa("1234567")).thenReturn(List.of());
//
//        boolean result = userService.canRegisterUser(cadastroDTO);
//
//        assertTrue(result, "Expected the user to be eligible for registration");
//    }
//
//    @Test
//    void testCanRegisterUser_UserAlreadyExists() {
//        CadastroDTO cadastroDTO = mock(CadastroDTO.class);
//        when(cadastroDTO.getRa()).thenReturn("1234567");
//        when(cadastroDTO.getSenha()).thenReturn("validPassword");
//
//        User existingUser = mock(User.class);
//        when(userRepository.findUserByRa("1234567")).thenReturn(List.of(existingUser));
//
//        boolean result = userService.canRegisterUser(cadastroDTO);
//
//        assertFalse(result, "Expected the user not to be eligible for registration due to existing RA");
//    }
//
//    @Test
//    void testCanRegisterUser_PasswordTooShort() {
//        CadastroDTO cadastroDTO = mock(CadastroDTO.class);
//        when(cadastroDTO.getRa()).thenReturn("1234567");
//        when(cadastroDTO.getSenha()).thenReturn("short");
//
//        when(userRepository.findUserByRa("1234567")).thenReturn(List.of());
//
//        boolean result = userService.canRegisterUser(cadastroDTO);
//
//        assertFalse(result, "Expected the user not to be eligible for registration due to short password");
//    }

    @Test
    void testGenerateRaToken_ReturnsRaForExistingUser() {
        String ra = "1234567";

        User user = mock(User.class);
        when(user.getRa()).thenReturn(ra);

        when(userRepository.findUserByRa(ra)).thenReturn(List.of(user));

        String result = userService.generateRaToken(ra);

        assertEquals(ra, result, "Expected the RA to be returned for an existing user");
    }

    @Test
    void testGenerateRaToken_ReturnsNullForNonExistentUser() {
        String ra = "1234567";

        when(userRepository.findUserByRa(ra)).thenReturn(List.of());

        String result = userService.generateRaToken(ra);

        assertNull(result, "Expected null for a non-existent user");
    }


}