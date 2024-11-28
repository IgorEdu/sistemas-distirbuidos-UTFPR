package com.UTFPR.server.service;

import com.UTFPR.domain.dto.CadastroDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.entities.User;
import jakarta.persistence.PersistenceException;

import java.io.IOException;

public class CadastroUsuarioFacade {
    private final UserService userService;
    private final ResponseService responseService;
    private final ResponseFormatter responseFormatter;

    public CadastroUsuarioFacade(UserService userService, ResponseService responseService, ResponseFormatter responseFormatter) {
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
    }

    public String handleCadastroUsuario(CadastroDTO cadastroDTO, String clientAddress) throws IOException {
        String formattedResponse;
        ResponseDTO responseDTO;


        try {
            if (!userService.isValidRa(cadastroDTO) || !userService.isValidPassword(cadastroDTO) || !userService.isValidName(cadastroDTO)) {

                System.out.println("RA valido: " + userService.isValidRa(cadastroDTO));
                System.out.println("Senha valida: " + userService.isValidPassword(cadastroDTO));
                System.out.println("Nome valido: " + userService.isValidName(cadastroDTO));

                responseDTO = responseService.createErrorResponse(
                        cadastroDTO.getOperacao(),
                        "Os campos recebidos nao sao validos."

                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                return formattedResponse;
            }

            if (userService.isExistentUser(cadastroDTO)) {
                responseDTO = responseService.createErrorResponse(
                        cadastroDTO.getOperacao(),
                        "Não foi cadastrar pois o usuario informado ja existe"
                );

                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                return formattedResponse;
            }

            User user = cadastroDTO.toEntity();
            userService.registerUser(user);

            responseDTO = responseService.createSuccessResponseWithMessage("Cadastro realizado com sucesso.");
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    cadastroDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }
        catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    cadastroDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }
        return formattedResponse;
    }
}
