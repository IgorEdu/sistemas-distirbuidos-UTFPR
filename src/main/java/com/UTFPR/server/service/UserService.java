package com.UTFPR.server.service;

import com.UTFPR.domain.dto.CadastroDTO;
import com.UTFPR.domain.entities.User;
import com.UTFPR.domain.contracts.CredentialProvider;
import com.UTFPR.server.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidRa(CredentialProvider credentialProvider) {
        String ra = credentialProvider.getRa();

        if(ra == null) return false;

        return ra.matches("^[0-9]+$") && ra.length() == 7;
    }

    public boolean isValidPassword(CredentialProvider credentialProvider) {
        String senha = credentialProvider.getSenha();

        if(senha == null) return false;

        return senha.length() >= 8 && senha.length() <= 20 && senha.matches("^[a-zA-Z]+$");
    }

    public boolean isValidName(CadastroDTO cadastroDTO) {
        String nome = cadastroDTO.getNome();

        if(nome == null) return false;

        return nome.length() <= 50 &&
                nome.matches("^[A-Z\\s]+$");
    }

    public boolean isValidName(String nome) {
        if(nome == null) return false;

        return nome.length() <= 50 &&
                nome.matches("^[A-Z\\s]+$");
    }

    public boolean isExistentUser(CredentialProvider credentialProvider) {
        List<User> users = userRepository.findUserByRa(credentialProvider.getRa());
        return !users.isEmpty();
    }

    public boolean isAuthenticate(CredentialProvider credentialProvider) {
        List<User> users = userRepository.findUserByRa(credentialProvider.getRa());
        return !users.isEmpty() && users.get(0).isSenha(credentialProvider.getSenha());
    }

    public boolean canRegisterUser(CadastroDTO cadastroDTO) {
        List<User> users = userRepository.findUserByRa(cadastroDTO.getRa());
        return users.isEmpty() && cadastroDTO.getSenha().length() >= 8;
    }

    @Transactional
    public void registerUser(User user) {
        userRepository.save(user);
    }

    public String generateRaToken(String ra) {
        List<User> users = userRepository.findUserByRa(ra);
        return users.isEmpty() ? null : users.get(0).getRa();
    }

    public User getUserByRa(String ra) {
        List<User> users = userRepository.findUserByRa(ra);
        return users.isEmpty() ? null : users.get(0);
    }

    public boolean isAdminByToken(String token){
        List<User> users = userRepository.findUserByRa(token);
        return !users.isEmpty() && users.get(0).isAdmin();
    }

    @Transactional
    public void deleteUser(User user){
        userRepository.deleteUser(user);
    }

    @Transactional
    public void editUserById(Long id, User userEdited){
        userRepository.editUserById(id, userEdited);
    }
}
