package com.models;

public class Server {
    String text;
    String textUpperCase;

    public Server(){
    }

    public String convertText(String text) {
        textUpperCase = text.toUpperCase();
        return textUpperCase;
    }

    public String getTextUpperCase() {
        return textUpperCase;
    }
}
