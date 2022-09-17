package br.com.ewerton.spring.data.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cargos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;

    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionario;

    @Override
    public String toString() {
        return "Id: " + id + " Descricao: " + descricao;
    }
}
