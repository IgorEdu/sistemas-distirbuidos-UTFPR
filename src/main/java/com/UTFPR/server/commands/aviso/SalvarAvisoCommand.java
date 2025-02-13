package com.UTFPR.server.commands.aviso;

import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.dto.SalvarAvisoDTO;
import com.UTFPR.domain.entities.Notice;
import com.UTFPR.server.service.*;
import com.UTFPR.shared.commands.Command;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.io.PrintWriter;

public class SalvarAvisoCommand implements Command {
    private SalvarAvisoDTO salvarAvisoDTO;
    private UserService userService;
    private NoticeService noticeService;
    private CategoryService categoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public SalvarAvisoCommand(SalvarAvisoDTO salvarAvisoDTO, UserService userService, NoticeService noticeService, CategoryService categoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.salvarAvisoDTO = salvarAvisoDTO;
        this.userService = userService;
        this.noticeService = noticeService;
        this.categoryService = categoryService;
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
            if (!userService.isAdminByToken(salvarAvisoDTO.getToken())) {
                responseDTO = responseService.createErrorResponse(
                        salvarAvisoDTO.getOperacao(),
                        "Usuario nao autorizado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            if(categoryService.getCategoryById(salvarAvisoDTO.getAviso().getCategoria()) == null){
                responseDTO = responseService.createErrorResponse(
                        salvarAvisoDTO.getOperacao(),
                        "Categoria não encontrada"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            if(salvarAvisoDTO.getAviso().getId() == 0){
                noticeService.registerNotice(salvarAvisoDTO.getAviso().toNotice(categoryService));
                responseDTO = responseService.createSuccessResponseWithMessage(salvarAvisoDTO.getOperacao(),
                        "Aviso salvo com sucesso");
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            Notice oldNotice = noticeService.getNoticeById(salvarAvisoDTO.getAviso().getId());
            Notice newNotice = salvarAvisoDTO.getAviso().toNotice(categoryService);

            if (oldNotice == null) {
                responseDTO = responseService.createErrorResponse(
                        salvarAvisoDTO.getOperacao(),
                        "Aviso nao encontrado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            noticeService.editNoticeById((int) oldNotice.getId(), newNotice);
            responseDTO = responseService.createSuccessResponseWithMessage(salvarAvisoDTO.getOperacao(),
                    "Aviso salvo com sucesso");
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    salvarAvisoDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        } catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    salvarAvisoDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}
