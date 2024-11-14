package com.UTFPR.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer2 extends Thread{
        static String convertText(String text) {
            return text.toUpperCase();
        }

        public static void main(String[] args) {
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
                return;  // Tratamento de erro, mas o servidor continua
            }

            System.out.println("Servidor iniciado na porta: " + portServer);
            System.out.println("Aguardando a conexão...");

            // Loop para aceitar múltiplos clientes
            while (true) {
                try {
                    // Aceita uma nova conexão de cliente (bloqueante)
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Conexão aceita de " + clientSocket.getInetAddress());

                    // Processa a comunicação com o cliente
                    try{
                        String inputLine;
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        while ((inputLine = in.readLine()) != null) {
                            String textConverted = convertText(inputLine);
                            System.out.println("Server: " + textConverted);
                            out.println(textConverted);

                            // Encerra a conexão do cliente se ele enviar "Bye."
                            if (inputLine.equalsIgnoreCase("Bye.")) {
                                System.out.println("Cliente desconectado: " + clientSocket.getInetAddress());
                                in.close();
                                out.close();
                                break;
                            }
                        }

                    } catch (IOException e) {
                        System.err.println("Erro na comunicação com o cliente: " + e.getMessage());
                    } finally {
                        // Fecha o socket do cliente, mas não o servidor
                        clientSocket.close();
                    }

                } catch (IOException e) {
                    System.err.println("Erro ao aceitar conexão: " + e.getMessage());
                }
            }
        }
}
