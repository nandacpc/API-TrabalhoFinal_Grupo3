package org.serratec.shablau.repository;


import org.serratec.shablau.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	boolean findByNomeIgnoreCase(String nome);
}
