package br.com.ewerton.spring.data.repository;

import br.com.ewerton.spring.data.domain.Cargo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long> {}
