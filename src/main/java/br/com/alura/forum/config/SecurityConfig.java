package br.com.alura.forum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.alura.forum.service.AutenticacaoService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoService autenticacaoService;
	
	//configura servicos de seguranca de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(autenticacaoService) //utiliza servico de autenticacao
			.passwordEncoder(new BCryptPasswordEncoder()); //utiliza um gerador de hash para senhas
	}
	//configura servicos de seguranca de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/topicos").permitAll() //autoriza apenas requisicoes do tipo GET
				.antMatchers(HttpMethod.GET, "/topicos/*").permitAll() 
				.anyRequest().authenticated() //as requiscoes que nao sao GET so com autenticacao
				.and().formLogin(); //abre um formulario padrao
	}
	//configura servicos de seguranca de arquivos estaticos (js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
	
	}
	
	//metodo para testar gerador de hash da senha
	public static void main(String[] args) {
		String senha = new BCryptPasswordEncoder().encode("123456");
		System.out.print(senha);
	}
		
}
