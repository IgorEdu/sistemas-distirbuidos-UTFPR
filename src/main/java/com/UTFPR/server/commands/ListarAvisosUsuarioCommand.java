package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.AvisoDTO;
import com.UTFPR.domain.dto.OperacaoComTokenERaDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.entities.Notice;
import com.UTFPR.domain.entities.User;
import com.UTFPR.server.service.*;
import com.UTFPR.shared.commands.Command;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ListarAvisosUsuarioCommand implements Command {
    private OperacaoComTokenERaDTO operacaoComTokenERaDTO;
    private UserService userService;
    private UserCategoryService userCategoryService;
    private NoticeService noticeService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public ListarAvisosUsuarioCommand(OperacaoComTokenERaDTO operacaoComTokenERaDTO, UserService userService, UserCategoryService userCategoryService, NoticeService noticeService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.operacaoComTokenERaDTO = operacaoComTokenERaDTO;
        this.userService = userService;
        this.userCategoryService = userCategoryService;
        this.noticeService = noticeService;
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
            if (!(operacaoComTokenERaDTO.getRa().equals(operacaoComTokenERaDTO.getToken())) &&
                    !userService.isAdminByToken(operacaoComTokenERaDTO.getToken())) {
                responseDTO = responseService.createErrorResponse(
                        operacaoComTokenERaDTO.getOperacao(),
                        "Usuario nao autorizado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            User user = userService.getUserByRa(operacaoComTokenERaDTO.getRa());


            if (user == null) {
                responseDTO = responseService.createErrorResponse(
                        operacaoComTokenERaDTO.getOperacao(),
                        "Usuario nao encontrado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            List<Notice> avisos = userCategoryService.getAllNoticesByUser(user, noticeService);

            responseDTO = responseService.returnSuccessResponseListNotices(operacaoComTokenERaDTO.getOperacao(), avisos);

            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            out.println(formattedResponse);
            return;
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    operacaoComTokenERaDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        } catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    operacaoComTokenERaDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}

