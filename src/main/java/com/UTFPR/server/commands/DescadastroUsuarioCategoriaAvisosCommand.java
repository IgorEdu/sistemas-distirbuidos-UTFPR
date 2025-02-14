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

public class DescadastroUsuarioCategoriaAvisosCommand implements Command {
    private CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO descadastrarUsuarioCategoriaAvisosDTO;
    private UserService userService;
    private CategoryService categoryService;
    private UserCategoryService userCategoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public DescadastroUsuarioCategoriaAvisosCommand(CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO descadastrarUsuarioCategoriaAvisosDTO, UserService userService, CategoryService categoryService, UserCategoryService userCategoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.descadastrarUsuarioCategoriaAvisosDTO = descadastrarUsuarioCategoriaAvisosDTO;
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
            if (!(descadastrarUsuarioCategoriaAvisosDTO.getRa().equals(descadastrarUsuarioCategoriaAvisosDTO.getToken())) &&
                    !userService.isAdminByToken(descadastrarUsuarioCategoriaAvisosDTO.getToken())) {
                responseDTO = responseService.createErrorResponse(
                        descadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                        "Usuario nao autorizado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            User user = userService.getUserByRa(descadastrarUsuarioCategoriaAvisosDTO.getRa());


            if (user == null) {
                responseDTO = responseService.createErrorResponse(
                        descadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                        "Usuario nao encontrado"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            Category category = categoryService.getCategoryById(descadastrarUsuarioCategoriaAvisosDTO.getCategoria());

            if (category == null) {
                responseDTO = responseService.createErrorResponse(
                        descadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                        "Categoria nao encontrada"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            UserCategory userCategory = new UserCategory(user, category);

            if(!userCategoryService.isExistentRealtionshipUserCategory(userCategory)){
                responseDTO = responseService.createErrorResponse(
                        descadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                        "Usuário não está vinculado à essa categoria"
                );
                formattedResponse = responseFormatter.formatResponse(responseDTO);
                System.out.println("Server (" + clientAddress + "): " + formattedResponse);
                out.println(formattedResponse);
                return;
            }

            userCategory = userCategoryService.getRelationshipUserCategory(userCategory);
            userCategoryService.deleteUserCategory(userCategory);
            responseDTO = responseService.createSuccessResponseWithMessage(descadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                    "Descadastro realizado com sucesso.");
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            out.println(formattedResponse);
            return;
        } catch (PersistenceException e) {
            responseDTO = responseService.createErrorResponse(
                    descadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                    "O servidor nao conseguiu conectar com o banco de dados"
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        } catch (Exception e) {
            responseDTO = responseService.createErrorResponse(
                    descadastrarUsuarioCategoriaAvisosDTO.getOperacao(),
                    "Erro interno no servidor."
            );
            formattedResponse = responseFormatter.formatResponse(responseDTO);
            System.out.println("Server (" + clientAddress + "): " + formattedResponse);
            e.printStackTrace();
        }

        out.println(formattedResponse);
    }
}

