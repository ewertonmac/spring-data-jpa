package br.com.ewerton.spring.data.service;

import br.com.ewerton.spring.data.domain.Cargo;
import br.com.ewerton.spring.data.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository repository;

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
        System.out.println("Informe a descricao do cargo");
        String descricao = scanner.next();
        var cargo = Cargo.builder().descricao(descricao.toUpperCase()).build();

        repository.save(cargo);
        System.out.println("Cargo salvo com sucesso");
    }
    private void atualizar(Scanner scanner) {
        System.out.println("Informe o id do cargo que deseja atualizar");
        Long id = scanner.nextLong();
        var optionalCargo = buscarPorId(id);

        if(optionalCargo.isPresent()){
            var cargo = optionalCargo.get();
            System.out.println("Informe a nova descricao do cargo");
            String descricao = scanner.next();
            cargo.setDescricao(descricao.toUpperCase());

            repository.save(cargo);
            System.out.println("Atualizado");
        }
        else {
            System.out.println("Cargo não encontrado");
        }
    }
    public void listar(){
        var cargos = repository.findAll();
        cargos.forEach(System.out::println);
    }
    private void deletar(Scanner scanner){
        System.out.println("Informe o id do cargo que deseja deletar");
        Long id = scanner.nextLong();

        repository.deleteById(id);
        System.out.println("Deletado");
    }
    public Optional<Cargo> buscarPorId(Long cargoId) {
        return repository.findById(cargoId);
    }
}
