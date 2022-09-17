package br.com.ewerton.spring.data.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "unidades_trabalho")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UnidadeTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String endereco;

    @ManyToMany(mappedBy = "unidadeTrabalho", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios;

    @Override
    public String toString() {
        return "ID: " + id +
                " DESCRICAO: " + descricao +
                " ENDERECO: " + endereco;
    }
}
