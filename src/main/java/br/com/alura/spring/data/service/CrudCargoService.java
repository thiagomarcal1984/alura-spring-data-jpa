package br.com.alura.spring.data.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {
    private Boolean system = true;
    private final CargoRepository cargoRepository;

    public CrudCargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual ação de cargo deseja executar? ");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");
            switch (scanner.nextInt()) {
                case 1:
                    salvar(scanner);
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

    private void salvar(Scanner scanner) {
        System.out.println("Descrição do cargo");
        String descricao = scanner.next();
        
        Cargo cargo = new Cargo();
        cargo.setDescricao(descricao);
        cargoRepository.save(cargo);

        System.out.println("Salvo.");
    }

    private void atualizar(Scanner scanner) {
        System.out.println("Informe a ID do cargo que será alterado: ");
        Integer id = scanner.nextInt();

        Optional<Cargo> resultado = cargoRepository.findById(id);
        
        if (resultado.isPresent()) {
            Cargo cargo = resultado.get();
            System.out.println("Cargo localizado: " + cargo);
            
            System.out.println("Informe a nova descrição do cargo:");
            String descricao = scanner.next();
            
            cargo.setDescricao(descricao);
            cargoRepository.save(cargo);
    
            System.out.println("Atualizado.");
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

    private void visualizar() {
        Iterable<Cargo> cargos = cargoRepository.findAll();
        cargos.forEach(cargo -> System.out.println(cargo));
    }

    private void deletar(Scanner scanner) {
        System.out.println("Informe a ID para deletar o registro:");
        int id = scanner.nextInt();
        cargoRepository.deleteById(id);
        System.out.println("Deletado.");
    }
}
