package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.model.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
	
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	Page<Topico> buscarPorNomeCurso(@Param("nomeCurso") String nomeCurso, Pageable paginacao);
	
	List<Topico> findByCursoNome(String nomeCurso);
	//pode ser separado por _ o atributo e propriedade do atributo
	//List<Topico> findByCurso_Nome(String nomeCurso);
	
	//podemos fazer uma busca personalizada
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> buscarPorNomeCurso(@Param("nomeCurso") String nomeCurso);
	

}
