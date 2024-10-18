package org.serratec.shablau.repository;

import java.util.List;

import org.serratec.shablau.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	List<Categoria> findByNomeIgnoreCase(String nome);
}
