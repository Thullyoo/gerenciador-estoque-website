package br.thullyoo.gerenciador_estoque_backend.service;

import br.thullyoo.gerenciador_estoque_backend.dto.request.UserRequest;
import br.thullyoo.gerenciador_estoque_backend.entity.User;
import br.thullyoo.gerenciador_estoque_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(UserRequest userRequest) {

        User user = new User();
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setName(user.getName());

        return userRepository.save(user);
    }

}
