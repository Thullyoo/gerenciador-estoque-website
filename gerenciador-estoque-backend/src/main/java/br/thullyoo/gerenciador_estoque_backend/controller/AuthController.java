package br.thullyoo.gerenciador_estoque_backend.controller;

import br.thullyoo.gerenciador_estoque_backend.dto.request.LoginRequest;
import br.thullyoo.gerenciador_estoque_backend.dto.response.TokenResponse;
import br.thullyoo.gerenciador_estoque_backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(authService.login(loginRequest)));
    }

}
