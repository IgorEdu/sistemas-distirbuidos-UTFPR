package com.UTFPR.server.commands.categoria;

import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesCategoriaDTO;
import com.UTFPR.domain.entities.Category;
import com.UTFPR.server.service.CategoryService;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.shared.commands.Command;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.io.PrintWriter;

public class InformacoesCategoriaCommand implements Command {
    private SolicitaInformacoesCategoriaDTO solicitaInformacoesCategoriaDTO;
    private CategoryService categoryService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public InformacoesCategoriaCommand(SolicitaInformacoesCategoriaDTO solicitaInformacoesCategoriaDTO, CategoryService categoryService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.solicitaInformacoesCategoriaDTO = solicitaInformacoesCategoriaDTO;
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

            responseDTO = responseService.returnSuccessResponseCategoryInformations(solicitaInformacoesCategoriaDTO.getOperacao(),category);
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
