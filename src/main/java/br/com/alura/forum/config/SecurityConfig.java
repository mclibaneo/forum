package br.com.alura.forum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.filter.AutenticacaoViaTokenFilter;
import br.com.alura.forum.repository.UsuarioRepository;
import br.com.alura.forum.service.AutenticacaoService;
import br.com.alura.forum.service.TokenService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private AutenticacaoService autenticacaoService; //dispara processo de autenticacao usuario e senha
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository repository;
	
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
				.antMatchers(HttpMethod.POST, "/auth").permitAll()
				.anyRequest().authenticated() //as requiscoes que nao sao GET so com autenticacao
				.and().csrf().disable() //desabilita CROSS SITE REQUEST FORGERY
				//.and().formLogin(); //abre um formulario padrao (nao habilitamos, pois estamos utilziando token)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //informa que a sessao e do tipo stateless
				.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class); //eh p rodar o authfilter antes do userfilter
				//qnd n usamos sessao precisamos informar ao spring o controller de autenticacao
				
	}
	//configura servicos de seguranca de arquivos estaticos (js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
	
	}
	
	@Override
	@Bean //transforma em bean para spring injetar na AutenticacaoController
	protected AuthenticationManager authenticationManager() throws Exception {	
		return super.authenticationManager();
	}
	
	//metodo para testar gerador de hash da senha
	public static void main(String[] args) {
		String senha = new BCryptPasswordEncoder().encode("123456");
		System.out.print(senha);
	}
		
}
