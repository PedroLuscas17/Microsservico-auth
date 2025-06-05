package gerenciadorDeProjetos.Apresentação;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gerenciadorDeProjetos.Aplicação.DTOs.LoginRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.LoginResponse;
import gerenciadorDeProjetos.Aplicação.Serviços.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private AuthenticationService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(authService.validateToken(token.replace("Bearer ", "")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
