package com.UTFPR.server.commands.categoria;

import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.dto.SalvarCategoriaDTO;
import com.UTFPR.domain.entities.Category;
import com.UTFPR.server.service.CategoryService;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.server.service.UserService;
import com.UTFPR.shared.commands.Command;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.io.PrintWriter;

public class SalvarCategoriaCommand implements Command {
    private SalvarCategoriaDTO salvarCategoriaDTO;
    private UserService userService;
    private CategoryService categoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public  SalvarCategoriaCommand(SalvarCategoriaDTO salvarCategoriaDTO, UserService userService, CategoryService categoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.salvarCategoriaDTO = salvarCategoriaDTO;
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
            if (!userService.isAdminByToken(salvarCategoriaDTO.getToken())) {
                responseDTO = responseService.createErrorResponse(
                        salvarCategoriaDTO.getOperacao(),
                        "Usuario nao autorizado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            if(salvarCategoriaDTO.getCategoria().getId() == 0){
                categoryService.registerCategory(salvarCategoriaDTO.getCategoria().toCategory());
                responseDTO = responseService.createSuccessResponseWithMessage(salvarCategoriaDTO.getOperacao(),
                        "Categoria salva com sucesso");
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            Category oldCategory = categoryService.getCategoryById(salvarCategoriaDTO.getCategoria().getId());
            Category newCategory = salvarCategoriaDTO.getCategoria().toCategory();

            if (oldCategory == null) {
                responseDTO = responseService.createErrorResponse(
                        salvarCategoriaDTO.getOperacao(),
                        "Categoria nao encontrada"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            categoryService.editCategoryById((int) oldCategory.getId(), newCategory);
            responseDTO = responseService.createSuccessResponseWithMessage(salvarCategoriaDTO.getOperacao(),
                    "Categoria salva com sucesso");
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    salvarCategoriaDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        } catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    salvarCategoriaDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}
