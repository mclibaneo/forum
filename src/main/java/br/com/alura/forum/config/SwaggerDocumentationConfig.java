package br.com.alura.forum.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.model.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocumentationConfig {

	/*
	 * Para configurar quais endpoints e pacotes da API o Swagger deve gerar a documentação
	 * */
	@Bean
	public Docket forumApi() {
		System.out.println("Entrou no Swagger");
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
					.paths(PathSelectors.ant("/**"))
					.build()
					.ignoredParameterTypes(Usuario.class)
					.globalOperationParameters(Arrays.asList( //para informar que eh necessario um parametro o header
								new ParameterBuilder()
										.name("Authorization")
										.description("Header para token JWT")
										.modelRef(new ModelRef("string"))
										.parameterType("header")
										.required(false)
										.build()
					));
	}
}
