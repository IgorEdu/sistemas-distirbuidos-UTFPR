package com.UTFPR;

import java.net.*;
import java.io.*;
import java.util.List;

import com.UTFPR.commands.Command;
import com.UTFPR.commands.CommandInvoker;
import com.UTFPR.domain.dto.OperacaoDTO;
import com.UTFPR.domain.entities.User;
import com.UTFPR.factory.CommandFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.UTFPR.repository.UserRepository;
import com.UTFPR.service.ResponseFormatter;
import com.UTFPR.service.ResponseService;
import com.UTFPR.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Server extends Thread {
    protected Socket clientSocket;

    public static void main(String[] args) throws IOException {
        int portServer = 20001;  // Porta padrão
        ServerSocket serverSocket = null;

        try {
            if (args.length > 0) {
                portServer = Integer.parseInt(args[0]);
            } else {
                System.out.println("Digite a porta (ou pressione Enter para usar a porta padrão 20001): ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String portInput = br.readLine();
                if (!portInput.isEmpty()) {
                    portServer = Integer.parseInt(portInput);
                }
            }
            serverSocket = new ServerSocket(portServer);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + portServer);
            return;
        }

        System.out.println("Servidor iniciado na porta: " + portServer);

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql-sistemas-distribuidos")) {
            EntityManager em = emf.createEntityManager();

            String jpql = "SELECT u FROM usuarios u WHERE u.ra = :ra AND u.isAdmin = :is_admin";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("ra", 9999999);
            query.setParameter("is_admin", true);
            List<User> admin = query.getResultList();

            if (admin.isEmpty()) {
                User user = new User(9999999, "admin", "admin");
                user.setAdmin(true);

                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();

                System.out.println("Admin cadastrado");
            }

            em.close();
        } catch (Exception e) {
            System.out.println("Erro ao validar admin: " + e.getMessage());
        }

        try {
            while (true) {
                System.out.println("Aguardando conexão");
                new Server(serverSocket.accept());
            }
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port: " + portServer);
                System.exit(1);
            }
        }
    }

    private Server(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }

    public void run() {
        System.out.println("Nova Thread iniciada");
        System.out.println("Conexão aceita de " + clientSocket.getInetAddress() + "\n");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql-sistemas-distribuidos");

        try {
            String inputLine;
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                OperacaoDTO operacaoDTO = objectMapper.readValue(inputLine, OperacaoDTO.class);
                UserRepository userRepository = new UserRepository(emf);
                UserService userService = new UserService(userRepository);
                ResponseService responseService = new ResponseService();
                ResponseFormatter responseFormatter = new ResponseFormatter(new ObjectMapper());

                CommandFactory commandFactory = new CommandFactory(userService, responseService, responseFormatter, out);
                Command command = commandFactory.createCommand(operacaoDTO, inputLine);
                CommandInvoker invoker = new CommandInvoker();
                invoker.executeCommand(command);
//                if (operacaoDTO.getOperacao().equals("login")) {
//                    System.out.println("Login solicitado");
//                    System.out.println("Client (" + clientSocket.getInetAddress() + "): " + inputLine);
//
//                    if (operacaoDTO.getOperacao().equals("login")) {
//                        EntityManager em = emf.createEntityManager();
//                        LoginDTO loginDTO = objectMapper.readValue(inputLine, LoginDTO.class);
//
//                        String jpql = "SELECT u FROM usuarios u WHERE u.ra = :ra";
//                        TypedQuery<User> query = em.createQuery(jpql, User.class);
//                        query.setParameter("ra", loginDTO.getRa());
//                        List<User> users = query.getResultList();
//
//                        if (!users.isEmpty() && users.get(0).isSenha(loginDTO.getSenha())) {
//                            ResponseDTO responseDTO = new ResponseDTO();
//                            responseDTO.setStatus(200);
//                            responseDTO.setToken(users.get(0).getNome());
//
//                            String response = objectMapper.writeValueAsString(responseDTO);
//                            System.out.println("Server (" + clientSocket.getInetAddress() + "): " + response);
//                            out.println(response);
//                        } else {
//                            ResponseDTO responseDTO = new ResponseDTO();
//                            responseDTO.setStatus(401);
//                            responseDTO.setMensagem("Erro ao realizar login.");
//
//                            String response = objectMapper.writeValueAsString(responseDTO);
//                            System.out.println("Server (" + clientSocket.getInetAddress() + "): " + response);
//                            out.println(response);
//                        }
//
//                        em.close();
//                    }
//                }
//
//                if (operacaoDTO.getOperacao().equals("cadastrarUsuario")) {
//                    System.out.println("Client (" + clientSocket.getInetAddress() + "): " + inputLine);
//                    CadastroDTO cadastroDTO = objectMapper.readValue(inputLine, CadastroDTO.class);
//                    User user = cadastroDTO.toEntity();
//
//                    EntityManager em = emf.createEntityManager();
//
//                    String jpql = "SELECT u FROM usuarios u WHERE u.ra = :ra";
//                    TypedQuery<User> query = em.createQuery(jpql, User.class);
//                    query.setParameter("ra", cadastroDTO.getRa());
//                    List<User> users = query.getResultList();
//
//                    if (users.isEmpty() && cadastroDTO.getSenha().length() >= 8) {
//                        em.getTransaction().begin();
//                        em.persist(user);
//                        em.getTransaction().commit();
//
//                        ResponseDTO responseDTO = new ResponseDTO();
//                        responseDTO.setStatus(200);
//                        responseDTO.setMensagem("Cadastro realizado com sucesso.");
//                        String response = objectMapper.writeValueAsString(responseDTO);
//                        System.out.println("Server (" + clientSocket.getInetAddress() + "): " + response);
//                        out.println(response);
//                    } else {
//                        ResponseDTO responseDTO = new ResponseDTO();
//                        responseDTO.setStatus(401);
//                        if (cadastroDTO.getSenha().length() < 8) {
//                            responseDTO.setMensagem("Erro ao cadastrar. Senha deve ter no mínimo 8 caracteres.");
//                        } else {
//                            responseDTO.setMensagem("Erro ao cadastrar.");
//                        }
//                        String response = objectMapper.writeValueAsString(responseDTO);
//                        System.out.println("Server (" + clientSocket.getInetAddress() + "): " + response);
//                        out.println(response);
//                    }
//                    em.close();
//                }
//
//                if (operacaoDTO.getOperacao().equals("logout")) {
//                    ResponseDTO responseDTO = new ResponseDTO();
//                    responseDTO.setStatus(200);
//                    String response = objectMapper.writeValueAsString(responseDTO);
//
//                    System.out.println("Client (" + clientSocket.getInetAddress() + "): " + inputLine);
//                    System.out.println("Cliente desconectado: " + clientSocket.getInetAddress());
//                    System.out.println("Server (" + clientSocket.getInetAddress() + "): " + response);
//                    out.println(response);
//
//                    out.close();
//                    in.close();
//                    clientSocket.close();
//                    break;
//                }
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            e.printStackTrace();
            System.exit(1);
        }
    }
}