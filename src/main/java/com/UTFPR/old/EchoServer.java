package com.UTFPR.old;

import java.net.*;
import java.io.*;

public class EchoServer
{
    static String convertText(String text) {
        String textUpperCase;
        textUpperCase = text.toUpperCase();
        return textUpperCase;
    }

    public static void main(String[] args) throws IOException
    {
        int portServer = 0;
        ServerSocket serverSocket = null;

        try {
            System.out.println("Digite a porta: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            portServer = Integer.parseInt(br.readLine());
            serverSocket = new ServerSocket(portServer);
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: " + portServer);
            System.exit(1);
        }

        Socket clientSocket = null;
        System.out.println ("Waiting for connection.....");

        try {
            clientSocket = serverSocket.accept();
        }
        catch (IOException e)
        {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        System.out.println ("Connection successful");
        System.out.println ("Waiting for input.....");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader( clientSocket.getInputStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null)
        {
            String textConverted = convertText(inputLine);
            System.out.println ("Server: " + textConverted);
            out.println(textConverted);

            if (inputLine.equals("Bye."))
                break;
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
} 