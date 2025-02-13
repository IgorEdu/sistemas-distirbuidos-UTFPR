package com.UTFPR.server.commands.aviso;

import com.UTFPR.domain.dto.OperacaoListarAvisosDTO;
import com.UTFPR.server.service.*;
import com.UTFPR.shared.commands.Command;

import java.io.IOException;
import java.io.PrintWriter;

public class ListarAvisosCommand implements Command {
    private OperacaoListarAvisosDTO operacaoListarAvisosDTO;
    private NoticeService noticeService;
    private CategoryService categoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public ListarAvisosCommand(OperacaoListarAvisosDTO operacaoListarAvisosDTO, NoticeService noticeService, CategoryService categoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.operacaoListarAvisosDTO = operacaoListarAvisosDTO;
        this.noticeService = noticeService;
        this.categoryService = categoryService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
        this.clientAddress = clientAddress;
    }

    @Override
    public void execute() throws IOException {
//        String formattedResponse;
//        ResponseDTO responseDTO;
//
//        try {
//            List<Notice> notices = noticeService.getAllNoticesOfCategory(operacaoListarAvisosDTO.getId());
//
//            Category category = categoryService.getCategoryById(operacaoListarAvisosDTO.getId());
//
//            responseDTO = responseService.returnSuccessResponseListNotices(operacaoListarAvisosDTO.getOperacao(),notices,category);
//            formattedResponse = responseFormatter.formatResponse(responseDTO);
//            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//        } catch (PersistenceException e) {
//            responseDTO = responseService.createErrorResponse(
//                    operacaoListarAvisosDTO.getOperacao(),
//                    "O servidor nao conseguiu conectar com o banco de dados"
//            );
//            formattedResponse = responseFormatter.formatResponse(responseDTO);
//            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//            e.printStackTrace();
//        }
//        catch (Exception e) {
//            responseDTO = responseService.createErrorResponse(
//                    operacaoListarAvisosDTO.getOperacao(),
//                    "Erro interno no servidor."
//            );
//            formattedResponse = responseFormatter.formatResponse(responseDTO);
//            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//            e.printStackTrace();
//        }
//
//        out.println(formattedResponse);
    }
}
