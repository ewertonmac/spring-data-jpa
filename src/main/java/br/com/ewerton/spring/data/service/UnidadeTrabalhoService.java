package br.com.ewerton.spring.data.service;

import br.com.ewerton.spring.data.domain.UnidadeTrabalho;
import br.com.ewerton.spring.data.repository.UnidadeTrabalhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class UnidadeTrabalhoService {
    private final UnidadeTrabalhoRepository repository;

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
        System.out.println("Informe a descricao");
        String descricao = scanner.next();
        System.out.println("Informe o endereco");
        String endereco = scanner.next();
        var unidade = UnidadeTrabalho.builder()
                .descricao(descricao)
                .endereco(endereco)
                .build();

        repository.save(unidade);
        System.out.println("Salvo com sucesso");
    }
    private void atualizar(Scanner scanner){
        System.out.println("Informe o Id");
        Long id = scanner.nextLong();
        var optionalUnidade = buscarPorId(id);

        if(optionalUnidade.isPresent()){
            var unidade = optionalUnidade.get();
            System.out.println("Informe a descricao");
            String descricao = scanner.next();
            System.out.println("Informe o endereco");
            String endereco = scanner.next();

            unidade.setDescricao(descricao);
            unidade.setEndereco(endereco);

            repository.save(unidade);
            System.out.println("Atualizado com sucesso");
        }
        else {
            System.out.println("Unidade não encontrada");
        }
    }
    public void listar(){
        var unidade = repository.findAll();
        unidade.forEach(System.out::println);
    }
    private void deletar(Scanner scanner){
        System.out.println("Informe o id da unidade que deseja deletar");
        Long id = scanner.nextLong();

        repository.deleteById(id);
        System.out.println("Deletada");
    }
    public Optional<UnidadeTrabalho> buscarPorId(Long unidadeId) {
        return repository.findById(unidadeId);
    }
}
