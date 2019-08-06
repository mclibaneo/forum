package br.com.alura.forum.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;

@RestController
public class WebController {

	@RequestMapping(name = "/topicos")
	public List<Topico> listar(){
		Topico topico = new Topico("Duvida"
							,"Duvida em curso"
							, new Curso("Java", "Desenvolvimento"));
		return Arrays.asList(topico, topico, topico);
	}
}
