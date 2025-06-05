package gerenciadorDeProjetos.Aplicação.Serviços;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gerenciadorDeProjetos.Aplicação.DTOs.LoginRequest;
import gerenciadorDeProjetos.Aplicação.DTOs.LoginResponse;
import gerenciadorDeProjetos.Infraestrutura.JwtUtil;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IAdministradorRepositorio;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IAlunoRepositorio;
import gerenciadorDeProjetos.Infraestrutura.Repositorios.IProfessorRepositorio;

@Service
public class AuthenticationService {
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private IAdministradorRepositorio adminRepo;
    
    @Autowired
    private IProfessorRepositorio profRepo;
    
    @Autowired
    private IAlunoRepositorio alunoRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse authenticate(LoginRequest request) {
        // Tenta autenticar como administrador
        var admin = adminRepo.findByEmail(request.getEmail());
        if (admin.isPresent() && passwordEncoder.matches(request.getSenha(), admin.get().getSenha())) {
            String token = jwtUtil.generateToken(request.getEmail(), "ROLE_ADMIN");
            return new LoginResponse("ROLE_ADMIN", token, admin.get().getId());
        }

        // Tenta autenticar como professor
        var prof = profRepo.findByEmail(request.getEmail());
        if (prof.isPresent() && passwordEncoder.matches(request.getSenha(), prof.get().getSenha())) {
            String token = jwtUtil.generateToken(request.getEmail(), "ROLE_PROFESSOR");
            return new LoginResponse("ROLE_PROFESSOR", token, prof.get().getId());
        }

        // Tenta autenticar como aluno
        var aluno = alunoRepo.findByEmail(request.getEmail());
        if (aluno.isPresent() && passwordEncoder.matches(request.getSenha(), aluno.get().getSenha())) {
            String token = jwtUtil.generateToken(request.getEmail(), "ROLE_ALUNO");
            return new LoginResponse("ROLE_ALUNO", token, aluno.get().getId());
        }

        throw new RuntimeException("Credenciais inválidas");
    }

    public boolean validateToken(String token) {
        return jwtUtil.isValid(token);
    }
}
