package br.thullyoo.gerenciador_estoque_backend.service;

import br.thullyoo.gerenciador_estoque_backend.dto.request.LoginRequest;
import br.thullyoo.gerenciador_estoque_backend.entity.User;
import br.thullyoo.gerenciador_estoque_backend.repository.UserRepository;
import br.thullyoo.gerenciador_estoque_backend.security.UserDetailsImpl;
import br.thullyoo.gerenciador_estoque_backend.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDetailsService userDetailsService;

    public String login(LoginRequest loginRequest){
        Optional<User> user = userRepository.findByEmail(loginRequest.email());

        if (user.isEmpty() || !user.get().verifyPassword(encoder, loginRequest.password())){
            throw new BadCredentialsException("E-mail or Password invalid");
        }

        return jwtUtils.generateTokenByUserDetails((UserDetailsImpl) userDetailsService.loadUserByUsername(loginRequest.email()));

    }

}
