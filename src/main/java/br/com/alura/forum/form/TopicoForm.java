package br.com.alura.forum.form;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;

public class TopicoForm {

	private String nomeTopico;
	private String mensagem;
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
