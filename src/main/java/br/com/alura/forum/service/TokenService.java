package br.com.alura.forum.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.TokenApp;
import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {
	
	/*
	 * essas propriedades so sao acessiveis em componentes do spring
	 * */
	@Value("${forum.jwt.expiration}") //valores sao obtidos do application.properties
	private String expiration; //tempo de expiracao em ms
	@Value("${forum.jwt.secret}")
	private String secret; //chave de criptografia

	public String gerarToken(Authentication authentication) {
		Usuario principal = (Usuario) authentication.getPrincipal(); //obtem o usuario autenticado
		TokenApp tokenApp = new TokenApp(principal.getId().toString(), expiration, secret);		
		return Jwts.builder()
						.setIssuer(tokenApp.getIssuer()) //add o nome do objeto token
						.setSubject(tokenApp.getSubject()) //add o usuario no token
						.setIssuedAt(tokenApp.getDateCreation()) //add data de criacao do token
						.setExpiration(tokenApp.dateExpiration()) //add data de expiracao do token
						.signWith(tokenApp.getSignatureAlgorithm(), tokenApp.getSecret()) //add asinatura do token com criptografia
						.compact(); //gera o token como uma string
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token); //devolve o token valido ou joga exception
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;			
		}
	}
	/*
	 * Parse eh utilizado para extrair informacoes de token string
	 * */
	public Long getIdUsuario(String token) {		
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

	
}
