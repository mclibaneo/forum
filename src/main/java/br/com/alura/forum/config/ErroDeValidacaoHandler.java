package br.com.alura.forum.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.alura.forum.dto.ErroFormularioDto;

@RestControllerAdvice //interceptador de metodos
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource message;

	/*
	 * Este metodo intercepta o erro da response
	 * trata a mensagem e devolve para o usuario 
	 * no formato amigavel
	 * */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) //intercepta erros do tipo BAD_REQUEST
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroFormularioDto> handle(MethodArgumentNotValidException exception) {
		List<ErroFormularioDto> dto = new ArrayList<ErroFormularioDto>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(erro -> {
			String mensagem = message //utiliza o messageSource para compor uma mensagem com suporte a localizacao
								.getMessage(erro, LocaleContextHolder.getLocale());
			ErroFormularioDto erroFormulario = new ErroFormularioDto(erro.getField(), mensagem);
			dto.add(erroFormulario);
		});
		return dto;
	}
}
