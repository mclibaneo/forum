package br.com.alura.forum.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

	private String usuario;
	private String senha;

	public LoginForm(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
		
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsernamePasswordAuthenticationToken coverter() {
		return new UsernamePasswordAuthenticationToken(this.usuario, this.senha);
	}
	
	
}
