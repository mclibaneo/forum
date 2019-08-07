package br.com.alura.forum.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.model.StatusTopico;
import br.com.alura.forum.model.Topico;

public class DetalhesTopicoDto extends TopicoDto {
	
	private String nomeAutor;
	private StatusTopico status;
	private List<RespostaDto> respostas;
	
	public DetalhesTopicoDto(Topico topico) {
		super(topico);
		this.nomeAutor = topico.getAutor().getNome();
		this.status = topico.getStatus();
		this.respostas = new ArrayList<RespostaDto>();
		respostas.addAll(topico
							.getRespostas()
							.stream()
							.map(RespostaDto::new)
							.collect(Collectors.toList()));
		
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public List<RespostaDto> getRespostas() {
		return respostas;
	}

	
}
