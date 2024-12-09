package com.UTFPR.server.service;

import com.UTFPR.domain.dto.LoginDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginFacadeTest {

    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private LoginFacade loginFacade;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        responseService = mock(ResponseService.class);
        responseFormatter = mock(ResponseFormatter.class);
        loginFacade = new LoginFacade(userService, responseService, responseFormatter);
    }

    @Test
    void testHandleLogin_InvalidRa() throws Exception {
        LoginDTO loginDTO = new LoginDTO("login", "a123456", "senhateste");
        String clientAddress = "127.0.0.1";

        when(userService.isValidRa(loginDTO)).thenReturn(false);
        ResponseDTO responseDTO = responseService.createErrorResponse(
                loginDTO.getOperacao(),
                "Os campos recebidos nao sao validos."

        );
        String responseFormatted = responseFormatter.formatResponse(responseDTO);
        when(responseService.createErrorResponse("login", "Os campos recebidos nao sao validos."))
                .thenReturn(responseDTO);
        when(responseFormatter.formatResponse(responseDTO)).thenReturn("Formatted Response");

        String result = loginFacade.handleLogin(loginDTO, clientAddress);

        assertEquals("Formatted Response", result);
        verify(userService).isValidRa(loginDTO);
        verify(responseService).createErrorResponse("login", "Os campos recebidos nao sao validos.");
        verify(responseFormatter).formatResponse(responseDTO);
    }

    @Test
    void testHandleLogin_InvalidPassword() throws Exception {
        LoginDTO loginDTO = new LoginDTO("login", "1234567", "short");
        String clientAddress = "127.0.0.1";

        when(userService.isValidRa(loginDTO)).thenReturn(true);
        when(userService.isValidPassword(loginDTO)).thenReturn(false);
//        ResponseDTO responseDTO = new ResponseDTO(400, "Os campos recebidos nao sao validos.", null);
        ResponseDTO responseDTO = responseService.createErrorResponse(
                loginDTO.getOperacao(),
                "Os campos recebidos nao sao validos."
        );
//        when(responseService.createErrorResponse("login", "Os campos recebidos nao sao validos."))
//                .thenReturn(responseDTO);
        when(responseFormatter.formatResponse(responseDTO)).thenReturn("Formatted Response");

        String result = loginFacade.handleLogin(loginDTO, clientAddress);

        assertEquals("Formatted Response", result);
        verify(userService).isValidPassword(loginDTO);
    }

    @Test
    void testHandleLogin_UserDoesNotExist() throws Exception {
        LoginDTO loginDTO = new LoginDTO("login", "1234567", "senhateste");
        String clientAddress = "127.0.0.1";

        when(userService.isValidRa(loginDTO)).thenReturn(true);
        when(userService.isValidPassword(loginDTO)).thenReturn(true);
        when(userService.isExistentUser(loginDTO)).thenReturn(false);
//        ResponseDTO responseDTO = new ResponseDTO(401, "Credenciais incorretas.", null);
        ResponseDTO responseDTO = responseService.createErrorResponse(
                loginDTO.getOperacao(),
                "Credenciais incorretas."
        );
//        when(responseService.createErrorResponse("login", "Credenciais incorretas."))
//                .thenReturn(responseDTO);
        when(responseFormatter.formatResponse(responseDTO)).thenReturn("Formatted Response");

        String result = loginFacade.handleLogin(loginDTO, clientAddress);

        assertEquals("Formatted Response", result);
        verify(userService).isExistentUser(loginDTO);
    }

    @Test
    void testHandleLogin_Success() throws Exception {
        LoginDTO loginDTO = new LoginDTO("login", "1234567", "senhateste");
        String clientAddress = "127.0.0.1";

        when(userService.isValidRa(loginDTO)).thenReturn(true);
        when(userService.isValidPassword(loginDTO)).thenReturn(true);
        when(userService.isExistentUser(loginDTO)).thenReturn(true);
        when(userService.generateRaToken("1234567")).thenReturn("valid_token");
//        ResponseDTO responseDTO = new ResponseDTO(200, "Success", "valid_token");
        ResponseDTO responseDTO = responseService.createSuccessResponseWithToken("login", "valid_token");
        when(responseService.createSuccessResponseWithToken("login", "valid_token")).thenReturn(responseDTO);
        when(responseFormatter.formatResponse(responseDTO)).thenReturn("Formatted Response");

        String result = loginFacade.handleLogin(loginDTO, clientAddress);

        assertEquals("Formatted Response", result);
        verify(userService).generateRaToken("1234567");
    }

    @Test
    void testHandleLogin_DatabaseException() throws Exception {
        LoginDTO loginDTO = new LoginDTO("login", "1234567", "senhateste");
        String clientAddress = "127.0.0.1";

        when(userService.isValidRa(loginDTO)).thenReturn(true);
        when(userService.isValidPassword(loginDTO)).thenReturn(true);
        when(userService.isExistentUser(loginDTO)).thenThrow(new PersistenceException("Database error"));
//        ResponseDTO responseDTO = new ResponseDTO(500, "O servidor nao conseguiu conectar com o banco de dados", null);
        ResponseDTO responseDTO = responseService.createErrorResponse(
                loginDTO.getOperacao(),
                "O servidor nao conseguiu conectar com o banco de dados"
        );
//        when(responseService.createErrorResponse("login", "O servidor nao conseguiu conectar com o banco de dados"))
//                .thenReturn(responseDTO);
        when(responseFormatter.formatResponse(responseDTO)).thenReturn("Formatted Response");

        String result = loginFacade.handleLogin(loginDTO, clientAddress);

        assertEquals("Formatted Response", result);
        verify(responseService).createErrorResponse("login", "O servidor nao conseguiu conectar com o banco de dados");
    }
}