package gerenciadorDeProjetos.Aplicação.DTOs;

import org.springframework.stereotype.Component;

@Component
public class LoginRequest {
    private String email;
    private String senha;
    
    public LoginRequest() {
    	
    }
    
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
