package br.com.ewerton.spring.data.domain.specification;

import br.com.ewerton.spring.data.domain.Funcionario;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
public class FuncionarioSpecification {
    public Specification<Funcionario> nome(String nome){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
    }

    public Specification<Funcionario> cpf(String cpf){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cpf"), cpf);
    }

    public Specification<Funcionario> salario(BigDecimal salario){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("salario"), salario);
    }

    public Specification<Funcionario> dataContratacao(LocalDate min, LocalDate max){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.between(root.get("dataContratacao"), min, max);
    }
}
