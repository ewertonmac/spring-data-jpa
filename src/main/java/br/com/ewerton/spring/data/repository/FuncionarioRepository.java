package br.com.ewerton.spring.data.repository;

import br.com.ewerton.spring.data.domain.Funcionario;
import br.com.ewerton.spring.data.domain.projecoes.FuncionarioProjecao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Long>,
        JpaSpecificationExecutor<Funcionario> {

    //Derived query
    List<Funcionario> findByNome(String nome);

    //JPQL
    @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :dataContratacao")
    List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, BigDecimal salario, LocalDate dataContratacao);

    //Native QUery
    @Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data ", nativeQuery = true)
    List<Funcionario> findDataContratacaoMaior(LocalDate data);

    @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
    List<FuncionarioProjecao> findFuncionarioSalario();
}
