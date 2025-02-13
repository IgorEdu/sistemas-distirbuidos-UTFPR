package com.UTFPR.server.commands.aviso;

import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesAvisoDTO;
import com.UTFPR.domain.entities.Notice;
import com.UTFPR.server.service.*;
import com.UTFPR.shared.commands.Command;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.io.PrintWriter;

public class ExcluirAvisoCommand implements Command {
    private SolicitaInformacoesAvisoDTO solicitaInformacoesAvisoDTO;
    private UserService userService;
    private NoticeService noticeService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public ExcluirAvisoCommand(SolicitaInformacoesAvisoDTO solicitaInformacoesAvisoDTO, UserService userService, NoticeService noticeService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.solicitaInformacoesAvisoDTO = solicitaInformacoesAvisoDTO;
        this.userService = userService;
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
            if(!userService.isAdminByToken(solicitaInformacoesAvisoDTO.getToken())){
                responseDTO = responseService.createErrorResponse(
                        solicitaInformacoesAvisoDTO.getOperacao(),
                        "Usuario nao autorizado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            Notice notice = noticeService.getNoticeById(Integer.parseInt(solicitaInformacoesAvisoDTO.getId()));

            if(notice == null) {
                responseDTO = responseService.createErrorResponse(
                        solicitaInformacoesAvisoDTO.getOperacao(),
                        "Aviso nao encontrado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

//            if(noticeService.isPresentOnWarnings((int) category.getId())){
//                responseDTO = responseService.createErrorResponse(
//                        solicitaInformacoesAvisoDTO.getOperacao(),
//                        "Não foi possível excluir, a categoria já está alocada a um ou mais avisos."
//                );
//                formattedResponse = responseFormatter.formatResponse(responseDTO);
//                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//                out.println(formattedResponse);
//                return;
//            }

            noticeService.deleteNotice(notice);

            responseDTO = responseService.createSuccessResponseWithMessage(solicitaInformacoesAvisoDTO.getOperacao(),
                    "Exclusão realizada com sucesso");
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    solicitaInformacoesAvisoDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }
        catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    solicitaInformacoesAvisoDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}
