package br.com.alura.forum.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
public class WebController {

	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoDto> listar(String nomeCurso){
		List<Topico> topicos = new ArrayList<Topico>();
		if(nomeCurso == null) {
			topicos = topicoRepository.findAll();
		}else {
			topicos = topicoRepository.buscarPorNomeCurso(nomeCurso);
		}
		return TopicoDto.converterParaArray(topicos);
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> salvar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) { //este metodo retorna o status da resposta
		Topico topico = form.converterParaTopico(cursoRepository);
		topicoRepository.save(topico);
		URI uri = uriBuilder //estamos recebendo no parametro a uri da resposta
					.path("/topicos/{id}") //mapeando a uri dinamica
					.buildAndExpand(topico.getId()).toUri(); //substituindo o valor de {id} pelo do objeto
		return ResponseEntity //estamos retornando um resposa 201
				.created(uri) //a resposta 201 necessita da uri do recurso
				.body(new TopicoDto(topico)); //a resposta 201 necessita de um corpo, por isso enviamos nosso objetoDto
	}
}
