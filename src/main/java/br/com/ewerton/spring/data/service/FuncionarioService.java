package br.com.ewerton.spring.data.service;

import br.com.ewerton.spring.data.domain.Cargo;
import br.com.ewerton.spring.data.domain.Funcionario;
import br.com.ewerton.spring.data.domain.UnidadeTrabalho;
import br.com.ewerton.spring.data.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final CargoService cargoService;
    private final UnidadeTrabalhoService unidadeTrabalhoService;

    public void inicializar(Scanner scanner){
        System.out.println("Informe a acao que deseja realizar:");
        System.out.println("0 - voltar");
        System.out.println("1 - salvar");
        System.out.println("2 - atualizar");
        System.out.println("3 - listar");
        System.out.println("4 - deletar");


        Integer opcao = scanner.nextInt();

        switch (opcao){
            case 1 :
                salvar(scanner);
                break;
            case 2 :
                atualizar(scanner);
                break;
            case 3 :
                listar();
                break;
            case 4 :
                deletar(scanner);
                break;
            default :
                break;
        }
    }
    private void salvar(Scanner scanner){
        System.out.println("Informe o nome");
        String nome = scanner.next();
        System.out.println("Informe o cpf");
        String cpf = scanner.next();
        System.out.println("Informe o salario");
        BigDecimal salario = scanner.nextBigDecimal();
        LocalDate dataContratacao = LocalDate.now();
        System.out.println("Qual o id do cargo?");
        Long cargoId = scanner.nextLong();
        Optional<Cargo> cargo = cargoService.buscarPorId(cargoId);
        if(cargo.isPresent()){
            List<UnidadeTrabalho> unidades = incluirUnidades(scanner);

            var funcionario = Funcionario.builder()
                    .nome(nome)
                    .cpf(cpf)
                    .salario(salario)
                    .dataContratacao(dataContratacao)
                    .unidadeTrabalho(unidades)
                    .cargo(cargo.get())
                    .build();

            repository.save(funcionario);
            System.out.println("Salvo com sucesso");
        }
        else{
            System.out.println("Cargo não encontrado");
        }
    }

    private List<UnidadeTrabalho> incluirUnidades(Scanner scanner) {
        Boolean isTrue = true;
        List<UnidadeTrabalho> unidades = new ArrayList<>();

        while (isTrue){
            System.out.println("Digite o id da unidade (0 para sair)");
            Long id = scanner.nextLong();
            Optional<UnidadeTrabalho> unidadeTrabalhoOptional = unidadeTrabalhoService.buscarPorId(id);

            if(id != 0 && unidadeTrabalhoOptional.isPresent()){
                unidades.add(unidadeTrabalhoOptional.get());
            }
            else {
               isTrue = false;
            }
        }

        return unidades;
    }

    private void atualizar(Scanner scanner){
        System.out.println("Informe o Id");
        Long id = scanner.nextLong();
        var funcionarioOptional = buscarPorId(id);

        if(funcionarioOptional.isPresent()){
            System.out.println("Informe o nome");
            String nome = scanner.next();
            System.out.println("Informe o cpf");
            String cpf = scanner.next();
            System.out.println("Informe o salario");
            BigDecimal salario = scanner.nextBigDecimal();


            var funcionario = funcionarioOptional.get();
            funcionario.setNome(nome);
            funcionario.setCpf(cpf);
            funcionario.setSalario(salario);

            repository.save(funcionario);
            System.out.println("Atualizado com sucesso");
        }
        else{
            System.out.println("funcionario não encontrado");
        }
    }

    private Optional<Funcionario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    private void listar(){
        var funcionarios = repository.findAll();
        funcionarios.forEach(System.out::println);
    }
    private void deletar(Scanner scanner){
        System.out.println("Informe o id do funcionario que deseja deletar");
        Long id = scanner.nextLong();

        repository.deleteById(id);
        System.out.println("Deletado");
    }
}
