package br.com.ewerton.spring.data.service;

import br.com.ewerton.spring.data.domain.Cargo;
import br.com.ewerton.spring.data.domain.Funcionario;
import br.com.ewerton.spring.data.domain.UnidadeTrabalho;
import br.com.ewerton.spring.data.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


        int opcao = scanner.nextInt();

        switch (opcao){
            case 1 :
                salvar(scanner);
                break;
            case 2 :
                atualizar(scanner);
                break;
            case 3 :
                listar(scanner);
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
        boolean isTrue = true;
        List<UnidadeTrabalho> unidades = new ArrayList<>();

        while (isTrue){
            System.out.println("Digite o id da unidade (0 para sair)");
            long id = scanner.nextLong();
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

    private void listar(Scanner scanner){
        var execucao = true;
        Integer pageNumber = 0;
        while (execucao){
            pageNumber = Math.max(pageNumber - 1, 0);
            Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.DESC, "nome"));
            var page = repository.findAll(pageable);

            System.out.println("Exibindo página: " + (page.getNumber() + 1)  + " de " + page.getTotalPages());
            if(page.getTotalElements() == 0){
                System.out.println("Nenhum elemento encontrado");
                execucao = false;
            }else {
                System.out.println("Total de elementos: " + page.getTotalElements());
                page.getContent().forEach(System.out::println);
                System.out.println("Qual página você deseja vizualizar?  para voltar digite 0");
                pageNumber = scanner.nextInt();
            }

            if(pageNumber.equals(0)){
                execucao = false;
            }
        }
    }
    private void deletar(Scanner scanner){
        System.out.println("Informe o id do funcionario que deseja deletar");
        Long id = scanner.nextLong();

        repository.deleteById(id);
        System.out.println("Deletado");
    }
}
