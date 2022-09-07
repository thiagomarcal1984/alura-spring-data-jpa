package br.com.alura.spring.data.service;

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
            switch (Integer.parseInt(System.console().readLine())) {
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
}
