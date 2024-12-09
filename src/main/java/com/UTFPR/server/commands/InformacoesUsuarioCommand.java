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
            User user = userService.getUserByRa(solicitaInformacoesUsuarioDTO.getRa());

            if(user.isAdmin() && !user.isSenha(solicitaInformacoesUsuarioDTO.getSenha())){
                responseDTO = responseService.createErrorResponse(
                        solicitaInformacoesUsuarioDTO.getOperacao(),
                        "Credenciais incorretas."
                );

                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
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


//        String response = loginFacade.handleLogin(loginDTO, clientAddress);
        out.println(formattedResponse);
    }
}
