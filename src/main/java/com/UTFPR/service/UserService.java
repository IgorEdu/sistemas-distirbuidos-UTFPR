package com.UTFPR.service;

import com.UTFPR.domain.dto.CadastroDTO;
import com.UTFPR.domain.dto.LoginDTO;
import com.UTFPR.domain.entities.User;
import com.UTFPR.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidUser(LoginDTO loginDTO) {
        List<User> users = userRepository.findUserByRa(loginDTO.getRa());
        return !users.isEmpty() && users.get(0).isSenha(loginDTO.getSenha());
    }

    public boolean canRegisterUser(CadastroDTO cadastroDTO) {
        List<User> users = userRepository.findUserByRa(cadastroDTO.getRa());
        return users.isEmpty() && cadastroDTO.getSenha().length() >= 8;
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public String generateToken(Integer ra) {
        List<User> users = userRepository.findUserByRa(ra);
        return users.isEmpty() ? null : users.get(0).getNome();
    }
}
