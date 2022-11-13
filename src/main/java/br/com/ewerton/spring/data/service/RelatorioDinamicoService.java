package br.com.ewerton.spring.data.service;

import br.com.ewerton.spring.data.domain.Funcionario;
import br.com.ewerton.spring.data.domain.specification.FuncionarioSpecification;
import br.com.ewerton.spring.data.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioDinamicoService {

    private final FuncionarioRepository repository;
    private String nome;
    private String cpf;
    private BigDecimal salario;
    private LocalDate dataInicial;
    private LocalDate dataFinal;


    public void inicializar(Scanner scanner){
        System.out.println("Escolha como deseja buscar: ");
        System.out.println("1 - AND");
        System.out.println("2 - OR");
        int opcao = scanner.nextInt();

        popularParametros(scanner);

        Specification<Funcionario> query = null;
        switch (opcao){
            case 1 :
                query = montaQuery("AND");
                break;
            case 2 :
                query = montaQuery("OR");
                break;
            default:
                break;
        }


        var resultado = repository.findAll(query);
        resultado.forEach(System.out::println);
    }

    private void popularParametros(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Informe o nome que deseja buscar, ou digite NULL para ignorar: ");
        String nome = scanner.next();
        System.out.println("Informe o cpf, ou digite NULL para ignorar: ");
        String cpf = scanner.next();
        System.out.println("Informe o salario, ou digite 0 para ignorar: ");
        BigDecimal salario = scanner.nextBigDecimal();
        System.out.println("Informe a data inicial, ou digite NULL para ignorar: (dd/mm/aaaa)");
        String dataInicialStr = scanner.next();

        this.nome = nome.equalsIgnoreCase("NULL") ? null : nome;
        this.cpf = cpf.equalsIgnoreCase("NULL") ? null : cpf;
        this.salario = salario.equals(BigDecimal.ZERO) ? null : salario;

        if(dataInicialStr.equalsIgnoreCase("NULL")){
            this.dataInicial = null;
            this.dataFinal = null;
        }else {
            LocalDate dataInicialInformada = LocalDate.parse(dataInicialStr, formatter);
            System.out.println("Informe a data final: (dd/mm/aaaa)");
            LocalDate dataFinalInformada = LocalDate.parse(scanner.next(), formatter);

            if(dataInicialInformada.isBefore(dataFinalInformada)){
                this.dataInicial = dataInicialInformada;
                this.dataFinal = dataFinalInformada;
            }else{
                this.dataFinal = dataInicialInformada;
                this.dataInicial = dataFinalInformada;
            }
        }
    }

    private Specification<Funcionario> montaQuery(String tipoQuery) {
        AtomicReference<Specification<Funcionario>> specification = new AtomicReference<>();

        var fields = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(f ->
                        !f.getType().equals(LocalDate.class) &&
                        !f.getName().equalsIgnoreCase("repository") &&
                        !f.getName().equalsIgnoreCase("formatter")
                )
                .collect(Collectors.toList());

        fields.forEach(f -> {
            try {
                f.setAccessible(true);
                Object value = f.get(this);

                if (value != null){
                    var metodo = FuncionarioSpecification.class.getMethod(f.getName(), f.getType());
                    var spec = (Specification<Funcionario>) metodo.invoke(new FuncionarioSpecification(), value);

                    if(specification.get() == null){
                        specification.set(spec);
                    }else if (tipoQuery.equalsIgnoreCase("AND")){
                        specification.set(specification.get().and(spec));
                    } else if (tipoQuery.equalsIgnoreCase("OR")) {
                        specification.set(specification.get().or(spec));
                    }
                }
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        adicionaDataContratacao(specification, tipoQuery);

        return specification.get();
    }

    private void adicionaDataContratacao(AtomicReference<Specification<Funcionario>> specification, String tipoQuery) {
        var f = new FuncionarioSpecification();

        if(dataInicial != null && dataFinal != null){
            if(specification.get() == null){
                specification.set(f.dataContratacao(dataInicial, dataFinal));
            }
            else if (tipoQuery.equalsIgnoreCase("AND")){
                specification.set(specification.get().and(f.dataContratacao(dataInicial, dataFinal)));
            }
            else if(tipoQuery.equalsIgnoreCase("OR")){
                specification.set(specification.get().or(f.dataContratacao(dataInicial, dataFinal)));
            }
        }
    }
}
