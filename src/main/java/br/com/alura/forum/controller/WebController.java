package br.com.alura.forum.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
public class WebController {

	@Autowired
	private TopicoRepository topicoRepository;

	@RequestMapping(name = "/topicos")
	public List<TopicoDto> listar(String nomeCurso){
		List<Topico> topicos = new ArrayList<Topico>();
		if(nomeCurso == null) {
			topicos = topicoRepository.findAll();
		}else {
			topicos = topicoRepository.buscarPorNomeCurso(nomeCurso);
		}
		return TopicoDto.converterParaArray(topicos);
	}
}
