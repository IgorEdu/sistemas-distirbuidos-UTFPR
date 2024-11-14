package com.UTFPR.old;

import java.net.*;
import java.io.*;

public class EchoServer3 extends Thread
{
    protected Socket clientSocket;

    static String convertText(String text) {
        return text.toUpperCase();
    }

    public static void main(String[] args) throws IOException
    {
        int portServer = 20001;  // Porta padrão
        ServerSocket serverSocket = null;

        try {
//            serverSocket = new ServerSocket(10008);
//            System.out.println ("Connection Socket Created");

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
//            System.out.println("Aguardando a conexão...");

            try {
                while (true)
                {
                    System.out.println ("Aguardando conexão");
                    new EchoServer3 (serverSocket.accept());
                }
            }
            catch (IOException e)
            {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        finally
        {
            try {
                serverSocket.close();
            }
            catch (IOException e)
            {
                System.err.println("Could not close port: " + portServer);
                System.exit(1);
            }
        }
    }

    private EchoServer3 (Socket clientSoc)
    {
        clientSocket = clientSoc;
        start();
    }

    public void run()
    {
        System.out.println ("Nova Thread iniciada");
        System.out.println("Conexão aceita de " + clientSocket.getInetAddress() + "\n");

        try {
            String inputLine;
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                String textConverted = convertText(inputLine);
                System.out.println("Server ("+ clientSocket.getInetAddress() +"): " + textConverted);
                out.println(textConverted);

                // Encerra a conexão do cliente se ele enviar "Bye."
                if (inputLine.equalsIgnoreCase("Bye.")) {
                    System.out.println("Cliente desconectado: " + clientSocket.getInetAddress());
                    in.close();
                    out.close();
                    break;
                }
            }

            out.close();
            in.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }
} 