package com.UTFPR.domain;

import java.net.Socket;

public class UsuarioAtivo {
    private String nome;
    private String ra;
    private String ip;
    private String socket;

    public UsuarioAtivo(String nome, String ra, String ip, String socket) {
        this.nome = nome;
        this.ra = ra;
        this.ip = ip;
        this.socket = socket;
    }

    public String getNome() {
        return nome;
    }

    public String getRa() {
        return ra;
    }

    public String getIp() {
        return ip;
    }

    public String getSocket() {
        return socket;
    }
}
