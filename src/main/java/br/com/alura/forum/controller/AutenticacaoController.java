package br.com.alura.forum.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.TokenDto;
import br.com.alura.forum.form.LoginForm;
import br.com.alura.forum.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired //nao eh injetado automaticamento pelo Spring
	private AuthenticationManager authManager; //vem da classe WebSecurityConfigurerAdapter
	
	@Autowired
	private TokenService tokenService; //classe q retorna um token
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.coverter(); //obtem um usuario autenticado
		try {
			Authentication authentication = authManager.authenticate(dadosLogin); //autentica o usuario obtido
			String token = tokenService.gerarToken(authentication); //gera o token conforme o usuario
			//Bearer é um dos mecanismos de autenticação utilizados no protocolo HTTP, tal como o Basic e o Digest.
			return ResponseEntity.ok(new TokenDto(token,"Bearer"));	//retorna o token no corpo da resposta			
		}catch (AuthenticationException ae) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
