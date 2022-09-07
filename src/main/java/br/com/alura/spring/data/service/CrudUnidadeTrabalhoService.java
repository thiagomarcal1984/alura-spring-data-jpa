package br.com.alura.spring.data.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Boolean system = true;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual ação de unidade de trabalho deseja executar? ");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");
            switch (scanner.nextInt()) {
                case 1:
                    salvar();
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    visualizar();
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void salvar() {
        System.out.println("Descrição da unidade de trabalho:");
        String descricao = System.console().readLine();
        
        System.out.println("Endereço da unidade de trabalho:");
        String endereco = System.console().readLine();

        UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
        unidadeTrabalho.setDescricao(descricao);
        unidadeTrabalho.setEndereco(endereco);

        unidadeTrabalhoRepository.save(unidadeTrabalho);

        System.out.println("Salvo.");
    }

    private void atualizar(Scanner scanner) {
        System.out.println("Informe a ID da unidade de trabalho que será alterada: ");
        Integer id = scanner.nextInt();

        Optional<UnidadeTrabalho> resultado = unidadeTrabalhoRepository.findById(id);
        
        if (resultado.isPresent()) {
            UnidadeTrabalho unidadeTrabalho = resultado.get();
            System.out.println("Unidade de Trabalho localizada: " + unidadeTrabalho);
            
            System.out.println("Descrição da unidade de trabalho:");
            String descricao = System.console().readLine();
            
            System.out.println("Endereço da unidade de trabalho:");
            String endereco = System.console().readLine();
    
            unidadeTrabalho.setDescricao(descricao);
            unidadeTrabalho.setEndereco(endereco);
    
            unidadeTrabalhoRepository.save(unidadeTrabalho);

            System.out.println("Atualizado.");
        } else {
            System.out.println("Unidade de Trabalho não encontrada.");
        }
    }

    private void visualizar() {
        Iterable<UnidadeTrabalho> unidadetrabalhos = unidadeTrabalhoRepository.findAll();
        unidadetrabalhos.forEach(unidadetrabalho -> System.out.println(unidadetrabalho));
    }

    private void deletar(Scanner scanner) {
        System.out.println("Informe a ID para deletar o registro:");
        int id = scanner.nextInt();
        unidadeTrabalhoRepository.deleteById(id);
        System.out.println("Deletado.");
    }
}
