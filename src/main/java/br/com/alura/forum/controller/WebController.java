package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.dto.DetalhesTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.AtualizacaoTopicoForm;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class WebController {

	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	@Cacheable(value = "listaTopicos")
	public Page<TopicoDto> listar(String nomeCurso, 
			@PageableDefault(sort="id", direction = Direction.DESC, page=0, size=10) Pageable paginacao){
		//realiza paginacao com os param na url, com valores default de ordenacao por id descendente
		Page<Topico> topicos; 
		if(nomeCurso == null) {
			topicos = topicoRepository.findAll(paginacao);
		}else {
			topicos = topicoRepository.buscarPorNomeCurso(nomeCurso, paginacao);
		}
		return TopicoDto.converterParaArray(topicos);
	}
	
	
	
	@PostMapping
	@Transactional
	@CacheEvict(cacheNames = "listaTopicos", allEntries = true) //anotacao para atualiziar o cache
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
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable(name = "id") Long id){
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			DetalhesTopicoDto detalhes = new DetalhesTopicoDto(topico.get()); 
			return ResponseEntity.ok().body(detalhes);
		}else {
			return ResponseEntity.badRequest().build(); //se nao houver recurso encaminha status de BAD_REQUEST
		}
	}
	
	@PutMapping("/{id}")
	@Transactional //comita a transacao ao final do metodo
	@CacheEvict(cacheNames = "listaTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
		//o param @requestBody configura o obj do corpo da resposta com a classe passada (devem ter os mesmos nomes de attrb)
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			Topico t = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok().body(new TopicoDto(t));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(cacheNames = "listaTopicos", allEntries = true)
	public ResponseEntity<Topico> remover(@PathVariable Long id){
		Optional<Topico> topicoOpt = topicoRepository.findById(id);
		if(topicoOpt.isPresent()) {
			topicoRepository.delete(topicoOpt.get());
			return ResponseEntity.ok().build();
		}else
		return ResponseEntity.badRequest().build();
		
	}
	
	
}
