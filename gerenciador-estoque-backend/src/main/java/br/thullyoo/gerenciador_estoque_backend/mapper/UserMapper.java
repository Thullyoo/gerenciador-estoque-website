package br.thullyoo.gerenciador_estoque_backend.mapper;

import br.thullyoo.gerenciador_estoque_backend.dto.request.UserRequest;
import br.thullyoo.gerenciador_estoque_backend.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    public static User toUser(UserRequest userRequest, PasswordEncoder passwordEncoder){
        User user = new User();
        user.setEmail(userRequest.email());
        user.setName(userRequest.name());
        user.setPassword(passwordEncoder.encode(userRequest.password()));

        return user;
    }
}
