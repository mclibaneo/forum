package br.com.alura.forum.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.forum.model.Topico;

public class TopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	public TopicoDto(Topico topico) {		
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	
	public static List<TopicoDto> converterParaArray(List<Topico> topicos){
		List<TopicoDto> listaTopicoDto = new ArrayList<>();
		for (Topico topico : topicos) {
			TopicoDto t = new TopicoDto(topico);
			listaTopicoDto.add(t);
		}
		return listaTopicoDto;
		//utilizando stream do java 8
		//return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	}

	
}
