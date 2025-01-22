package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.OperacaoComTokenDTO;
import com.UTFPR.domain.dto.ResponseDTO;
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
import java.util.List;

public class ListarCategoriasCommand implements Command {
    private OperacaoComTokenDTO operacaoComTokenDTO;
    private UserService userService;
    private CategoryService categoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public ListarCategoriasCommand(OperacaoComTokenDTO operacaoComTokenDTO, UserService userService, CategoryService categoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.operacaoComTokenDTO = operacaoComTokenDTO;
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
            List<Category> categories = categoryService.getAllCategories();

            responseDTO = responseService.returnSuccessResponseListCategories(operacaoComTokenDTO.getOperacao(),categories);
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    operacaoComTokenDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }
        catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    operacaoComTokenDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}
