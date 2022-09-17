package br.com.ewerton.spring.data.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "funcionarios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private BigDecimal salario;
    private LocalDate dataContratacao;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "funcionarios_unidades",
            joinColumns = {@JoinColumn(name = "fk_funcionario")},
            inverseJoinColumns = {@JoinColumn(name = "fk_unidade")}
    )
    private List<UnidadeTrabalho> unidadeTrabalho;

    @Override
    public String toString() {
        return "ID: " + id +
                " NOME: " + nome +
                " CPF: " + cpf +
                " SALARIO: " + salario +
                " DATA CONTRATACAO: " + dataContratacao +
                " CARGO: " + cargo.getDescricao() +
                " UNIDADE TARBALHO: " + unidadeTrabalho;
    }
}
