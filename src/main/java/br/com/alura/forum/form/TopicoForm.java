package br.com.alura.forum.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;

public class TopicoForm {

	@NotNull @NotBlank @Length(min = 5) //validacoes do BeanValidation
	private String nomeTopico;
	@NotNull @NotBlank @Length(min = 10)
	private String mensagem;
	@NotNull @NotBlank
	private String NomeCurso;
	
	public String getNomeTopico() {
		return nomeTopico;
	}
	public void setNomeTopico(String nomeTopico) {
		this.nomeTopico = nomeTopico;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getNomeCurso() {
		return NomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		NomeCurso = nomeCurso;
	}
	
	public Topico converterParaTopico(CursoRepository repository) {
		Curso curso = repository.findByNome(NomeCurso);
		Topico topico = new Topico(nomeTopico, mensagem, curso);
		return topico;
	}
	
}
