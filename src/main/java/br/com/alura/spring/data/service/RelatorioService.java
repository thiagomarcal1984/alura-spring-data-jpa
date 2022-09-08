package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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
            System.out.println("(-1) - Busca com Query JPQL");
            System.out.println("0 - Sair");
            System.out.println("1 - Busca funcionário nome");
            switch (Integer.parseInt(System.console().readLine())) {
                case -1:
                    queryEmRepository();
                    break;
                case 1:
                    buscaFuncionarioNome();
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

    private void queryEmRepository() {
        System.out.println(
            "Buscando o Zina, que ganha 5 mil e foi contratado "
            + "em 10/10/2000: ");
        
        List<Funcionario> lista = funcionarioRepository
            .findNomeSalarioMaiorDataContratacao(
                "ZINA", 
                new BigDecimal("5000"), 
                LocalDate.parse("2000-10-10")
            );
        
        lista.forEach(System.out::println);
    }
}
