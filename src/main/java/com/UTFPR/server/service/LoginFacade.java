package com.UTFPR.server.service;

import com.UTFPR.domain.dto.LoginDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import jakarta.persistence.PersistenceException;

import java.io.IOException;

public class LoginFacade {
    private final UserService userService;
    private final ResponseService responseService;
    private final ResponseFormatter responseFormatter;

    public LoginFacade(UserService userService, ResponseService responseService, ResponseFormatter responseFormatter) {
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
    }

    public String handleLogin(LoginDTO loginDTO, String clientAddress) throws IOException {
        String formattedResponse;
        ResponseDTO responseDTO;


        try {
            if (!userService.isValidRa(loginDTO) || !userService.isValidPassword(loginDTO)) {
                responseDTO = responseService.createErrorResponse(
                        loginDTO.getOperacao(),
                        "Os campos recebidos nao sao validos."

                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                return formattedResponse;
            }

            if (!userService.isAuthenticate(loginDTO)) {
                responseDTO = responseService.createErrorResponse(
                        loginDTO.getOperacao(),
                        "Credenciais incorretas."
                );

                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                return formattedResponse;
            }


            String token = userService.generateRaToken(loginDTO.getRa());
            responseDTO = responseService.createSuccessResponseWithToken(loginDTO.getOperacao(), token);
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    loginDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }
        catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    loginDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }
        return formattedResponse;
    }
}
