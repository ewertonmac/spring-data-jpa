package br.com.ewerton.spring.data.repository;

import br.com.ewerton.spring.data.domain.Funcionario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
}
