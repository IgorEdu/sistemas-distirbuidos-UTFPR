package com.UTFPR.old;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Digite o IP do servidor: ");
        String serverIP = br.readLine();

        System.out.println("Digite a porta do servidor: ");
        int serverPort = Integer.parseInt(br.readLine());

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // echoSocket = new Socket("taranis", 7);
            System.out.println("Conectado ao servidor " + serverIP + ":" + serverPort);
            System.out.println("Digite 'Bye.' para encerrar");
            echoSocket = new Socket(serverIP, serverPort);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Servidor não encontrado: " + serverIP + ":" + serverPort);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverIP + ":" + serverPort);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;

        System.out.print ("input: ");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("echo: " + in.readLine());
            System.out.print ("input: ");

            if (userInput.equals("Bye."))
                break;
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
