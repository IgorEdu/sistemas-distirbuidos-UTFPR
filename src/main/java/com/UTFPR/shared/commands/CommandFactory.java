package com.UTFPR.shared.commands;

import com.UTFPR.domain.dto.*;
import com.UTFPR.server.commands.*;
import com.UTFPR.server.commands.aviso.ExcluirAvisoCommand;
import com.UTFPR.server.commands.aviso.InformacoesAvisoCommand;
import com.UTFPR.server.commands.aviso.ListarAvisosCommand;
import com.UTFPR.server.commands.aviso.SalvarAvisoCommand;
import com.UTFPR.server.commands.categoria.ExcluirCategoriaCommand;
import com.UTFPR.server.commands.categoria.InformacoesCategoriaCommand;
import com.UTFPR.server.commands.categoria.ListarCategoriasCommand;
import com.UTFPR.server.commands.categoria.SalvarCategoriaCommand;
import com.UTFPR.server.commands.usuario.*;
import com.UTFPR.server.infra.DatabaseConnection;
import com.UTFPR.server.repository.CategoryRepository;
import com.UTFPR.server.repository.NoticeRepository;
import com.UTFPR.server.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;

public class CommandFactory {
    private UserService userService;
    private CategoryService categoryService;
    private NoticeService noticeService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;

    public CommandFactory(UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out) {
        this.userService = userService;
        this.categoryService = new CategoryService(new CategoryRepository(DatabaseConnection.getEntityManager()));
        this.noticeService = new NoticeService(new NoticeRepository(DatabaseConnection.getEntityManager()));
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
    }

    public Command createCommand(OperacaoDTO operacaoDTO, String inputLine, String clientAddress) throws IOException {

        System.out.println("Client (" + clientAddress + "): " + inputLine);
        if (operacaoDTO.getOperacao() == null) {
            return new FallbackCommand(operacaoDTO.getOperacao(),
                    "Operacao nao encontrada",
                    out, responseService, responseFormatter
            );
        }

        switch (operacaoDTO.getOperacao()) {
            case "login":
                LoginDTO loginDTO = null;
                try {
                    loginDTO = new ObjectMapper().readValue(inputLine, LoginDTO.class);
                    return new LoginCommand(loginDTO, userService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("login", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "cadastrarUsuario":
                try {
                    CadastroDTO cadastroDTO = new ObjectMapper().readValue(inputLine, CadastroDTO.class);
                    return new CadastroUsuarioCommand(cadastroDTO, userService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("cadastrarUsuario", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "logout":
                LogoutDTO logoutDTO = new ObjectMapper().readValue(inputLine, LogoutDTO.class);
                return new LogoutCommand(logoutDTO, userService, responseService, responseFormatter, out, clientAddress);
            case "localizarUsuario":
                try {
                    SolicitaInformacoesUsuarioDTO solicitaInformacoesUsuarioDTO = new ObjectMapper().readValue(inputLine, SolicitaInformacoesUsuarioDTO.class);
                    return new InformacoesUsuarioCommand(solicitaInformacoesUsuarioDTO, userService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("localizarUsuario", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "listarUsuarios":
                try {
                    OperacaoComTokenDTO operacaoComTokenDTO = new ObjectMapper().readValue(inputLine, OperacaoComTokenDTO.class);
                    return new ListarUsuariosCommand(operacaoComTokenDTO, userService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("listarUsuarios", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "excluirUsuario":
                try {
                    SolicitaInformacoesUsuarioDTO solicitaInformacoesUsuarioDTO = new ObjectMapper().readValue(inputLine, SolicitaInformacoesUsuarioDTO.class);
                    return new ExcluirUsuarioCommand(solicitaInformacoesUsuarioDTO, userService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("excluirUsuario", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "editarUsuario":
                try {
                    EditaUsuarioDTO editaUsuarioDTO = new ObjectMapper().readValue(inputLine, EditaUsuarioDTO.class);
                    System.out.println(editaUsuarioDTO.getUsuario().getRa());
                    return new EditarUsuarioCommand(editaUsuarioDTO, userService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("editarUsuario", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "salvarCategoria":
                try {
                    SalvarCategoriaDTO salvarCategoriaDTO = new ObjectMapper().readValue(inputLine, SalvarCategoriaDTO.class);
                    return new SalvarCategoriaCommand(salvarCategoriaDTO, userService, categoryService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("salvarCategoria", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "listarCategorias":
                try {
                    OperacaoComTokenDTO operacaoComTokenDTO = new ObjectMapper().readValue(inputLine, OperacaoComTokenDTO.class);
                    return new ListarCategoriasCommand(operacaoComTokenDTO, userService, categoryService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("listarCategorias", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "localizarCategoria":
                try {
                    SolicitaInformacoesCategoriaDTO solicitaInformacoesCategoriaDTO = new ObjectMapper().readValue(inputLine, SolicitaInformacoesCategoriaDTO.class);
                    return new InformacoesCategoriaCommand(solicitaInformacoesCategoriaDTO, categoryService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("localizarCategoria", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "excluirCategoria":
                try {
                    SolicitaInformacoesCategoriaDTO solicitaInformacoesCategoriaDTO = new ObjectMapper().readValue(inputLine, SolicitaInformacoesCategoriaDTO.class);
                    return new ExcluirCategoriaCommand(solicitaInformacoesCategoriaDTO, userService, categoryService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("excluirCategoria", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "salvarAviso":
                try {
                    SalvarAvisoDTO salvarAvisoDTO = new ObjectMapper().readValue(inputLine, SalvarAvisoDTO.class);
                    return new SalvarAvisoCommand(salvarAvisoDTO, userService, noticeService, categoryService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("salvarAviso", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "listarAvisos":
                try {
                    OperacaoListarAvisosDTO operacaoListarAvisosDTO = new ObjectMapper().readValue(inputLine, OperacaoListarAvisosDTO.class);
                    return new ListarAvisosCommand(operacaoListarAvisosDTO, noticeService, categoryService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("listarAvisos", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "localizarAviso":
                try {
                    SolicitaInformacoesAvisoDTO solicitaInformacoesAvisoDTO = new ObjectMapper().readValue(inputLine, SolicitaInformacoesAvisoDTO.class);
                    return new InformacoesAvisoCommand(solicitaInformacoesAvisoDTO, noticeService, categoryService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("localizarAviso", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "excluirAviso":
                try {
                    SolicitaInformacoesAvisoDTO solicitaInformacoesAvisoDTO = new ObjectMapper().readValue(inputLine, SolicitaInformacoesAvisoDTO.class);
                    return new ExcluirAvisoCommand(solicitaInformacoesAvisoDTO, userService, noticeService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO = responseService.createErrorResponse("excluirAviso", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
//            case "cadastrarUsuarioCategoria":
//                try {
//                    CadastrarUsuarioCategoriaAvisosDTO cadastrarUsuarioCategoriaAvisosDTO = new ObjectMapper().readValue(inputLine, CadastrarUsuarioCategoriaAvisosDTO.class);
//                    return new CadastrarUsuarioCategoriaAvisosCommand(cadastrarUsuarioCategoriaAvisosDTO, userService, noticeService, responseService, responseFormatter, out, clientAddress);
//                } catch (IOException e) {
//                    ResponseDTO responseDTO = responseService.createErrorResponse("cadastrarUsuarioCategoria", "Não foi possível ler o json recebido");
//                    String response = responseFormatter.formatResponse(responseDTO);
//                    System.out.println("Server (" + clientAddress + "): " + response);
//                    out.println(response);
//                }
            default:
                return new FallbackCommand(operacaoDTO.getOperacao(),
                        "Operacao nao encontrada",
                        out, responseService, responseFormatter
                );
        }
    }
}

