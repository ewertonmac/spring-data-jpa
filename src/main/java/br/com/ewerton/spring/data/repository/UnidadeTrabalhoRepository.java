package br.com.ewerton.spring.data.repository;

import br.com.ewerton.spring.data.domain.UnidadeTrabalho;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeTrabalhoRepository extends CrudRepository<UnidadeTrabalho, Long> {
}
