package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.EditaUsuarioDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesUsuarioDTO;
import com.UTFPR.domain.entities.User;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.server.service.UserService;
import com.UTFPR.shared.commands.Command;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class EditarUsuarioCommand implements Command {
    private EditaUsuarioDTO editaUsuarioDTO;
    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public EditarUsuarioCommand(EditaUsuarioDTO editaUsuarioDTO, UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.editaUsuarioDTO = editaUsuarioDTO;
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
        this.clientAddress = clientAddress;
    }

    @Override
    public void execute() throws IOException {
        String formattedResponse;
        ResponseDTO responseDTO;

        try {
            if (!Objects.equals(editaUsuarioDTO.getUsuarioDTO().getRa(), editaUsuarioDTO.getToken())) {
                responseDTO = responseService.createErrorResponse(
                        editaUsuarioDTO.getOperacao(),
                        "Usuario nao autorizado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            User oldUser = userService.getUserByRa(editaUsuarioDTO.getUsuarioDTO().getRa());
            User newUser = editaUsuarioDTO.getUsuarioDTO().toUser();

            if (oldUser == null) {
                responseDTO = responseService.createErrorResponse(
                        editaUsuarioDTO.getOperacao(),
                        "Usuario nao encontrado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            if (!userService.isValidRa(editaUsuarioDTO) || !userService.isValidPassword(editaUsuarioDTO) || !userService.isValidName(editaUsuarioDTO.getUsuarioDTO().getNome())) {

                System.out.println("RA valido: " + userService.isValidRa(editaUsuarioDTO));
                System.out.println("Senha valida: " + userService.isValidPassword(editaUsuarioDTO));
                System.out.println("Nome valido: " + userService.isValidName(editaUsuarioDTO.getUsuarioDTO().getNome()));

                responseDTO = responseService.createErrorResponse(
                        editaUsuarioDTO.getOperacao(),
                        "Os campos recebidos nao sao validos."

                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            userService.editUserById(oldUser.getId(), newUser);
            responseDTO = responseService.createSuccessResponseWithMessage(editaUsuarioDTO.getOperacao(),
                    "Edição realizada com sucesso");
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    editaUsuarioDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        } catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    editaUsuarioDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}
