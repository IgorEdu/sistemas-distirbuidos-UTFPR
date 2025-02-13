package com.UTFPR.server.commands.usuario;

import com.UTFPR.domain.dto.CadastrarUsuarioCategoriaAvisosDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.server.service.*;
import com.UTFPR.shared.commands.Command;

import java.io.IOException;
import java.io.PrintWriter;

public class CadastroUsuarioCategoriaAvisosCommand implements Command {
    private CadastrarUsuarioCategoriaAvisosDTO cadastrarUsuarioCategoriaAvisosDTO;
    private UserService userService;
    private CategoryService categoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public CadastroUsuarioCategoriaAvisosCommand(CadastrarUsuarioCategoriaAvisosDTO cadastrarUsuarioCategoriaAvisosDTO, UserService userService, CategoryService categoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.cadastrarUsuarioCategoriaAvisosDTO = cadastrarUsuarioCategoriaAvisosDTO;
        this.userService = userService;
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

//        try {
//            if (!userService.isAdminByToken(cadastrarUsuarioCategoriaAvisosDTO.getToken())) {
//                responseDTO = responseService.createErrorResponse(
//                        cadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
//                        "Usuario nao autorizado"
//                );
//                formattedResponse = responseFormatter.formatResponse(responseDTO);
//                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//                out.println(formattedResponse);
//                return;
//            }
//
//            User user = userService.getUserByRa(cadastrarUsuarioCategoriaAvisosDTO.getRa());
//
//            if (user == null) {
//                responseDTO = responseService.createErrorResponse(
//                        cadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
//                        "Usuario nao encontrado"
//                );
//                formattedResponse = responseFormatter.formatResponse(responseDTO);
//                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//                out.println(formattedResponse);
//                return;
//            }
//
//            Category category = categoryService.getCategoryById(cadastrarUsuarioCategoriaAvisosDTO.getCategoria());
//
//            if (category == null) {
//                responseDTO = responseService.createErrorResponse(
//                        cadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
//                        "Categoria nao encontrada"
//                );
//                formattedResponse = responseFormatter.formatResponse(responseDTO);
//                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//                out.println(formattedResponse);
//                return;
//            }
//
//            if(cadastrarUsuarioCategoriaAvisosDTO.getAviso().getId() == 0){
//                noticeService.registerNotice(salvarAvisoDTO.getAviso().toNotice());
//                responseDTO = responseService.createSuccessResponseWithMessage(salvarAvisoDTO.getOperacao(),
//                        "Aviso salvo com sucesso");
//                formattedResponse = responseFormatter.formatResponse(responseDTO);
//                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//                out.println(formattedResponse);
//                return;
//            }
//
//            Notice oldNotice = noticeService.getNoticeById(salvarAvisoDTO.getAviso().getId());
//            Notice newNotice = salvarAvisoDTO.getAviso().toNotice();
//
//            if (oldNotice == null) {
//                responseDTO = responseService.createErrorResponse(
//                        salvarAvisoDTO.getOperacao(),
//                        "Aviso nao encontrado"
//                );
//                formattedResponse = responseFormatter.formatResponse(responseDTO);
//                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//                out.println(formattedResponse);
//                return;
//            }
//
//            noticeService.editNoticeById((int) oldNotice.getId(), newNotice);
//            responseDTO = responseService.createSuccessResponseWithMessage(salvarAvisoDTO.getOperacao(),
//                    "Aviso salvo com sucesso");
//            formattedResponse = responseFormatter.formatResponse(responseDTO);
//            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//        } catch (PersistenceException e) {
//            responseDTO = responseService.createErrorResponse(
//                    salvarAvisoDTO.getOperacao(),
//                    "O servidor nao conseguiu conectar com o banco de dados"
//            );
//            formattedResponse = responseFormatter.formatResponse(responseDTO);
//            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
//            e.printStackTrace();
//        } catch (Exception e) {
//            responseDTO = responseService.createErrorResponse(
//                    salvarAvisoDTO.getOperacao(),
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

