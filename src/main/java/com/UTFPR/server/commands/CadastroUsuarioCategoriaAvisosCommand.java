package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.User;
import com.UTFPR.domain.entities.UserCategory;
import com.UTFPR.server.service.*;
import com.UTFPR.shared.commands.Command;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.io.PrintWriter;

public class CadastroUsuarioCategoriaAvisosCommand implements Command {
    private CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO;
    private UserService userService;
    private CategoryService categoryService;
    private UserCategoryService userCategoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public CadastroUsuarioCategoriaAvisosCommand(CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO, UserService userService, CategoryService categoryService, UserCategoryService userCategoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO = cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO;
        this.userService = userService;
        this.categoryService = categoryService;
        this.userCategoryService = userCategoryService;
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
            if (!(cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getRa().equals(cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getToken())) &&
                    !userService.isAdminByToken(cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getToken())) {
                responseDTO = responseService.createErrorResponse(
                        cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                        "Usuario nao autorizado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            User user = userService.getUserByRa(cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getRa());


            if (user == null) {
                responseDTO = responseService.createErrorResponse(
                        cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                        "Usuario nao encontrado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            Category category = categoryService.getCategoryById(cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getCategoria());

            if (category == null) {
                responseDTO = responseService.createErrorResponse(
                        cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                        "Categoria nao encontrada"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            UserCategory userCategory = new UserCategory(user, category);

            if(userCategoryService.isExistentRealtionshipUserCategory(userCategory)){
                responseDTO = responseService.createErrorResponse(
                        cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                        "Usuário já está vinculado à essa categoria"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            userCategoryService.registerUserCategory(userCategory);
            responseDTO = responseService.createSuccessResponseWithMessage(cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                    "Cadastro em categoria realizado com sucesso.");
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            out.println(formattedResponse);
            return;
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        } catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    cadastrarOuDescadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}

