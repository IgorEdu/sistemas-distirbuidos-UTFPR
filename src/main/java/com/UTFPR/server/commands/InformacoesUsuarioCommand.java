package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.LoginDTO;
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

public class InformacoesUsuarioCommand implements Command {
    private SolicitaInformacoesUsuarioDTO solicitaInformacoesUsuarioDTO;
    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public InformacoesUsuarioCommand(SolicitaInformacoesUsuarioDTO solicitaInformacoesUsuarioDTO, UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.solicitaInformacoesUsuarioDTO = solicitaInformacoesUsuarioDTO;
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
            if(!Objects.equals(solicitaInformacoesUsuarioDTO.getRa(), solicitaInformacoesUsuarioDTO.getToken()) && !userService.isAdminByToken(solicitaInformacoesUsuarioDTO.getToken())){
                responseDTO = responseService.createErrorResponse(
                        solicitaInformacoesUsuarioDTO.getOperacao(),
                        "Usuario nao autorizado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            User user = userService.getUserByRa(solicitaInformacoesUsuarioDTO.getRa());

            if(user == null) {
                responseDTO = responseService.createErrorResponse(
                        solicitaInformacoesUsuarioDTO.getOperacao(),
                        "Usuario nao encontrado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            responseDTO = responseService.returnSuccessResponseUserInformations(solicitaInformacoesUsuarioDTO.getOperacao(),user);
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    solicitaInformacoesUsuarioDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }
        catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    solicitaInformacoesUsuarioDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}
