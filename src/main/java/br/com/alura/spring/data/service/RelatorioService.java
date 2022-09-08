package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatorioService {
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    public void inicial(Scanner scanner) {
        Boolean system = true;
        while (system) {
            System.out.println("Qual ação de relatório deseja executar? ");
            System.out.println("0 - Sair");
            System.out.println("1 - Busca funcionário nome");
            System.out.println("2 - Busca funcionario nome, data contratacao e salario maior");
            switch (Integer.parseInt(System.console().readLine())) {
                case 1:
                    buscaFuncionarioNome();
                    break;
                case 2:
                    buscaFuncionarioNomeSalarioMaiorData();
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void buscaFuncionarioNome() {
        System.out.println("Digite o nome do funcionário que deseja pesquisar: ");
        String nome = System.console().readLine();
        
        List<Funcionario> list = funcionarioRepository.findByNomeContaining(nome);
        list.forEach(System.out::println);
    }

    private void buscaFuncionarioNomeSalarioMaiorData() {
        System.out.println("Qual nome deseja pesquisar?");
        String nome = System.console().readLine();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Qual data contrataçao deseja pesquisar? (Formato dd/MM/yyyy):");
        LocalDate data = LocalDate.parse(System.console().readLine(), formatter);

        System.out.println("Qual salário deseja pesquisar?");
        BigDecimal salario = new BigDecimal(System.console().readLine());
        
        List<Funcionario> lista = funcionarioRepository
            .findNomeSalarioMaiorDataContratacao(nome, salario, data);
        
        lista.forEach(System.out::println);
    }
}
