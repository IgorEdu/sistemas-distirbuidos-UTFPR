package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesAvisoDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesCategoriaDTO;
import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.Notice;
import com.UTFPR.server.service.CategoryService;
import com.UTFPR.server.service.NoticeService;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.shared.commands.Command;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.io.PrintWriter;

public class InformacoesAvisoCommand implements Command {
    private SolicitaInformacoesAvisoDTO  solicitaInformacoeAvisoDTO;
    private NoticeService noticeService;
    private CategoryService categoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public InformacoesAvisoCommand(SolicitaInformacoesAvisoDTO solicitaInformacoeAvisoDTO, NoticeService noticeService, CategoryService categoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this. solicitaInformacoeAvisoDTO =  solicitaInformacoeAvisoDTO;
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
//            Notice notice = noticeService.getNoticeById(Integer.parseInt(solicitaInformacoeAvisoDTO.getId()));
//
//            if(notice == null) {
//                responseDTO = responseService.createErrorResponse(
//                        solicitaInformacoeAvisoDTO.getOperacao(),
//                        "Aviso nao encontrada"
//                );
//                formattedResponse = responseFormatter.formatResponse(responseDTO);
//                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//                out.println(formattedResponse);
//                return;
//            }
//
//            Category category = categoryService.getCategoryById((int) notice.getCategoria().getId());
//
//            responseDTO = responseService.returnSuccessResponseNoticeInformations(solicitaInformacoeAvisoDTO.getOperacao(),notice, category);
//            formattedResponse = responseFormatter.formatResponse(responseDTO);
//            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//        } catch (PersistenceException e) {
//            responseDTO = responseService.createErrorResponse(
//                    solicitaInformacoeAvisoDTO.getOperacao(),
//                    "O servidor nao conseguiu conectar com o banco de dados"
//            );
//            formattedResponse = responseFormatter.formatResponse(responseDTO);
//            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//            e.printStackTrace();
//        }
//        catch (Exception e) {
//            responseDTO = responseService.createErrorResponse(
//                    solicitaInformacoeAvisoDTO.getOperacao(),
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
