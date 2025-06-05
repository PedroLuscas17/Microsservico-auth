package gerenciadorDeProjetos.Apresentação;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gerenciadorDeProjetos.Aplicação.DTOs.LoginRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.LoginResponse;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAdministradorAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IAlunoAppServiço;
import gerenciadorDeProjetos.Aplicação.Serviços.Interfaces.IProfessorAppServiço;
import gerenciadorDeProjetos.Infraestrutura.JwtUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private IAdministradorAppServiço admService;
    
    @Autowired
    private IProfessorAppServiço profService;
    
    @Autowired
    private IAlunoAppServiço alunoService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            // Tenta login como administrador
            if (admService.logar(request.getEmail(), request.getSenha())) {
                Long id = admService.buscarEmail(request.getEmail());
                String token = jwtUtil.generateToken(request.getEmail(), "ROLE_ADMIN");
                return ResponseEntity.ok(new LoginResponse("ROLE_ADMIN", token, id));
            }
            
            // Tenta login como professor
            if (profService.logar(request.getEmail(), request.getSenha())) {
                Long id = profService.buscarEmail(request.getEmail());
                String token = jwtUtil.generateToken(request.getEmail(), "ROLE_PROFESSOR");
                return ResponseEntity.ok(new LoginResponse("ROLE_PROFESSOR", token, id));
            }
            
            // Tenta login como aluno
            if (alunoService.logar(request.getEmail(), request.getSenha())) {
                Long id = alunoService.buscarEmail(request.getEmail());
                String token = jwtUtil.generateToken(request.getEmail(), "ROLE_ALUNO");
                return ResponseEntity.ok(new LoginResponse("ROLE_ALUNO", token, id));
            }
            
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
