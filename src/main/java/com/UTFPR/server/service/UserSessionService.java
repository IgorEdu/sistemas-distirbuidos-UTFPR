package com.UTFPR.server.service;

import com.UTFPR.controller.ServerOptionsController;
import com.UTFPR.domain.UsuarioAtivo;

import java.util.concurrent.ConcurrentHashMap;

public class UserSessionService {
    private static final ConcurrentHashMap<Thread, UsuarioAtivo> usuariosAtivos = new ConcurrentHashMap<>();
    private static ServerOptionsController serverOptionsController;

    public UserSessionService(ServerOptionsController serverOptionsController) {
        UserSessionService.serverOptionsController = serverOptionsController;
    }

    public static void atualizarUsuarioAtivo(String nome, String ra) {
        Thread currentThread = Thread.currentThread();

        UsuarioAtivo usuarioAntigo = usuariosAtivos.get(currentThread);

        UsuarioAtivo usuario = new UsuarioAtivo(nome, ra, usuarioAntigo.getIp(), usuarioAntigo.getSocket());
        usuariosAtivos.put(currentThread, usuario);
        System.out.println("Usuário ativo: " + nome + " (RA: " + ra + "), IP(" + usuario.getIp() + ") na Thread " + currentThread.getId());

        if (serverOptionsController != null && usuarioAntigo != null) {
            String usuarioInfo = "Usuario: " + usuarioAntigo.getNome() + " RA: " + usuarioAntigo.getRa() + " (" + usuarioAntigo.getIp() + ")";
            serverOptionsController.removerUsuario(usuarioInfo);
        }

        if (serverOptionsController != null) {
            String usuarioInfo = "Usuario: " + nome + " RA: " + ra + " (" + usuario.getIp() + ")";
            serverOptionsController.adicionarUsuarioConectado(usuarioInfo);
        }
    }

    public void adicionarUsuarioAtivo(String nome, String ra, String ip, String socket) {
        Thread currentThread = Thread.currentThread();
        UsuarioAtivo usuario = new UsuarioAtivo(nome, ra, ip, socket);
        usuariosAtivos.put(currentThread, usuario);
        System.out.println("Usuário ativo: " + nome + " (RA: " + ra + "), IP(" + ip + ") na Thread " + currentThread.getId());

        if (serverOptionsController != null) {
            String usuarioInfo = "Usuario: " + nome + " RA: " + ra + " (" + usuario.getIp() + ")";
            serverOptionsController.adicionarUsuarioConectado(usuarioInfo);
        }
    }

    public void removerUsuarioAtivo() {
        UsuarioAtivo usuario = null;
        Thread currentThread = Thread.currentThread();
        if (usuariosAtivos.containsKey(currentThread)) {
            usuario = usuariosAtivos.remove(currentThread);
            System.out.println("Usuário removido: " + usuario.getNome() + " (RA: " + usuario.getRa() + ")");
        }

        if (serverOptionsController != null && usuario != null) {
            String usuarioInfo = "Usuario: " + usuario.getNome() + " RA: " + usuario.getRa() + " (" + usuario.getIp() + ")";
            serverOptionsController.removerUsuario(usuarioInfo);
        }
    }

    public static ConcurrentHashMap<Thread, UsuarioAtivo> getUsuariosAtivos() {
        return usuariosAtivos;
    }
}
