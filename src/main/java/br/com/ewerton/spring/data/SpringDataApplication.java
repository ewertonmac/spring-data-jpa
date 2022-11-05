package br.com.ewerton.spring.data;

import br.com.ewerton.spring.data.service.CargoService;
import br.com.ewerton.spring.data.service.FuncionarioService;
import br.com.ewerton.spring.data.service.RelatorioService;
import br.com.ewerton.spring.data.service.UnidadeTrabalhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringDataApplication implements CommandLineRunner {

	private final CargoService cargoService;
	private final UnidadeTrabalhoService unidadeTrabalhoService;
	private final FuncionarioService funcionarioService;
	private final RelatorioService relatorioService;
	private Boolean system = true;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) {
		var scanner = new Scanner(System.in);
		while (system){
			System.out.println("Informe a opcao desejada");
			System.out.println("0 - sair");
			System.out.println("1 - cargo");
			System.out.println("2 - unidade");
			System.out.println("3 - funcionario");
			System.out.println("4 - Relatorio");

			int opcao = scanner.nextInt();

			switch (opcao){
				case 1:
					cargoService.inicializar(scanner);
					break;
				case 2:
					unidadeTrabalhoService.inicializar(scanner);
					break;
				case 3:
					funcionarioService.inicializar(scanner);
					break;
				case 4:
					relatorioService.inicializar(scanner);
					break;
				default:
					system = false;
			}
		}
	}
}
