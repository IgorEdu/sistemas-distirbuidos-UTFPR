package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesCategoriaDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesUsuarioDTO;
import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.User;
import com.UTFPR.server.service.CategoryService;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.server.service.UserService;
import com.UTFPR.shared.commands.Command;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class ExcluirCategoriaCommand implements Command {
    private SolicitaInformacoesCategoriaDTO solicitaInformacoesCategoriaDTO;
    private UserService userService;
    private CategoryService categoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public ExcluirCategoriaCommand(SolicitaInformacoesCategoriaDTO solicitaInformacoesCategoriaDTO, UserService userService, CategoryService categoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.solicitaInformacoesCategoriaDTO = solicitaInformacoesCategoriaDTO;
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

        try {
            if(!userService.isAdminByToken(solicitaInformacoesCategoriaDTO.getToken())){
                responseDTO = responseService.createErrorResponse(
                        solicitaInformacoesCategoriaDTO.getOperacao(),
                        "Usuario nao autorizado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            Category category = categoryService.getCategoryById(Integer.parseInt(solicitaInformacoesCategoriaDTO.getId()));

            if(category == null) {
                responseDTO = responseService.createErrorResponse(
                        solicitaInformacoesCategoriaDTO.getOperacao(),
                        "Categoria nao encontrada"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            if(categoryService.isPresentOnWarnings((int) category.getId())){
                responseDTO = responseService.createErrorResponse(
                        solicitaInformacoesCategoriaDTO.getOperacao(),
                        "Não foi possível excluir, a categoria já está alocada a um ou mais avisos."
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            categoryService.deleteCategory(category);

            responseDTO = responseService.createSuccessResponseWithMessage(solicitaInformacoesCategoriaDTO.getOperacao(),
                    "Exclusão realizada com sucesso");
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    solicitaInformacoesCategoriaDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }
        catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    solicitaInformacoesCategoriaDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}
