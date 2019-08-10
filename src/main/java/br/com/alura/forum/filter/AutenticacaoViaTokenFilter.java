package br.com.alura.forum.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import br.com.alura.forum.service.TokenService;

/*
 * classe filtro para validar o token antes de cada requisicao
 * */
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository repository;

	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {		
		String token = recuperarToken(request);
		boolean valido = tokenService.isTokenValido(token);		
		if(valido) {
			autenticarCliente(token);
		}
		filterChain.doFilter(request, response); //realiza o filtro e manda para frente		
	}

	private void autenticarCliente(String token) {
		Long idSubject = tokenService.getIdUsuario(token); 
		Usuario principal = repository.findById(idSubject).get();		
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(principal,null,principal.getAuthorities()); //usado para autenticar o cliente
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization"); //pega dados do cabecalho onde tem o token		
		//validacoes do token
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;			
		}
		return token.substring(7,token.length()); //retorna apenas o token, sem o 'Bearer'
	}

	
}
