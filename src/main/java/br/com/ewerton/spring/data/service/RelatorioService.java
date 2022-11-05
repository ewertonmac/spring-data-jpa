package br.com.ewerton.spring.data.service;

import br.com.ewerton.spring.data.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FuncionarioRepository repository;

    public void inicializar(Scanner scanner){
        System.out.println("Informe a acao que deseja realizar:");
        System.out.println("0 - voltar");
        System.out.println("1 - Buscar funcionario por nome");
        System.out.println("2 - Buscar funcionario por nome, salario e data contratação");
        System.out.println("3 - Buscar funcionario por data contratação");
        System.out.println("4 - Buscar funcionario por salario");



        int opcao = scanner.nextInt();

        switch (opcao){
            case 1 :
                listarPorNome(scanner);
                break;
            case 2 :
                listarPorNomeSalarioData(scanner);
                break;
            case 3 :
                listarPorDataContratacao(scanner);
                break;
            case 4 :
                listarFuncionarioSalario();
                break;
            default :
                break;
        }

    }

    private void listarPorNome(Scanner scanner) {
        System.out.println("Informe o nome do funcionario");
        String nome = scanner.next();
        var resultado = repository.findByNome(nome);
        if (!resultado.isEmpty()){
            System.out.println(resultado.size() + " funcionarios encontrados.");
            resultado.forEach(System.out::println);
        }
        else System.out.println("Nenhum funcionario encontrado");
    }

    private void listarPorNomeSalarioData(Scanner scanner) {
        System.out.println("Informe o nome do funcionario: ");
        String nome = scanner.next();
        System.out.println("Informe o salário: ");
        BigDecimal salario = scanner.nextBigDecimal();
        System.out.println("Informe a data de contratação: dd/mm/aaaa");
        var data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        var resultado = repository.findNomeSalarioMaiorDataContratacao(nome,salario,localDate);

        if(!resultado.isEmpty()){
            System.out.println(resultado.size() + " funcionarios encontrados.");
            resultado.forEach(System.out::println);
        }
        else System.out.println("Nenhum funcionario encontrado");
    }

    private void listarPorDataContratacao(Scanner scanner) {
        System.out.println("Informe a data de contratação: dd/mm/aaaa");
        var data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        var resultado = repository.findDataContratacaoMaior(localDate);

        if(!resultado.isEmpty()){
            System.out.println(resultado.size() + " funcionarios encontrados.");
            resultado.forEach(System.out::println);
        }
        else System.out.println("Nenhum funcionario encontrado");
    }

    private void listarFuncionarioSalario(){
        var funcionarios = repository.findFuncionarioSalario();
        funcionarios.forEach(f ->
                System.out.printf("ID: %s | Nome: %s | Salário: %s%n", f.getId(), f.getNome(), f.getSalario())
        );
    }
}
