package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {
    private Boolean system = true;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private UnidadeTrabalhoRepository unidadeTrabalhoRepository;


    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual ação de funcionario deseja executar? ");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");
            switch (Integer.parseInt(System.console().readLine())) {
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
        System.out.println("Nome do funcionario:");
        String descricao = System.console().readLine();
        
        System.out.println("CPF do funcionario:");
        String cpf = System.console().readLine();

        Optional<Cargo> cargo = cargoRepository.findById(-1);
        do {
            System.out.println("Informe a ID do cargo do funcionario:");
            Integer id = Integer.parseInt(System.console().readLine());
            cargo = cargoRepository.findById(id);
        } while (!cargo.isPresent());

        Optional<UnidadeTrabalho> unidadeTrabalho = unidadeTrabalhoRepository.findById(-1);
        do {
            System.out.println("Informe a ID da unidade de trabalho do funcionario:");
            Integer id = Integer.parseInt(System.console().readLine());
            unidadeTrabalho = unidadeTrabalhoRepository.findById(id);
        } while (!unidadeTrabalho.isPresent());

        System.out.println("Salário do funcionario:");
        BigDecimal salario = new BigDecimal(System.console().readLine());
        
        System.out.println("Data de contratação do funcionario:");
        LocalDate dataContratacao = LocalDate.parse(System.console().readLine());

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(descricao);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setCargo(cargo.get());
        funcionario.getUnidadesTrabalho().add(unidadeTrabalho.get());
        funcionario.setDataContratacao(dataContratacao);

        funcionarioRepository.save(funcionario);

        System.out.println("Salvo.");
    }

    private void atualizar(Scanner scanner) {
        System.out.println("Informe a ID do funcionario que será alterado: ");
        Integer id = Integer.parseInt(System.console().readLine());

        Optional<Funcionario> resultado = funcionarioRepository.findById(id);
        
        if (resultado.isPresent()) {
            Funcionario funcionario = resultado.get();
            System.out.println("Funcionario localizado: " + funcionario);
            
            System.out.println("Nome do funcionario:");
            String descricao = System.console().readLine();
            
            System.out.println("CPF do funcionario:");
            String cpf = System.console().readLine();
    
            Optional<Cargo> cargo = cargoRepository.findById(-1);
            do {
                System.out.println("Informe a ID do cargo do funcionario:");
                id = Integer.parseInt(System.console().readLine());
                cargo = cargoRepository.findById(id);
            } while (!cargo.isPresent());
    
            Optional<UnidadeTrabalho> unidadeTrabalho = unidadeTrabalhoRepository.findById(-1);
            do {
                System.out.println("Informe a ID da unidade de trabalho do funcionario:");
                id = Integer.parseInt(System.console().readLine());
                unidadeTrabalho = unidadeTrabalhoRepository.findById(id);
            } while (!unidadeTrabalho.isPresent());
    
            System.out.println("Salário do funcionario:");
            BigDecimal salario = new BigDecimal(System.console().readLine());
            
            System.out.println("Data de contratação do funcionario:");
            LocalDate dataContratacao = LocalDate.parse(System.console().readLine());
    
            funcionario.setNome(descricao);
            funcionario.setCpf(cpf);
            funcionario.setSalario(salario);
            funcionario.setCargo(cargo.get());
            funcionario.getUnidadesTrabalho().add(unidadeTrabalho.get());
            funcionario.setDataContratacao(dataContratacao);
    
            funcionarioRepository.save(funcionario);
    
        
            System.out.println("Atualizado.");
        } else {
            System.out.println("Funcionario não encontrado.");
        }
    }

    private void visualizar() {
        Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
        funcionarios.forEach(funcionario -> System.out.println(funcionario));
    }

    private void deletar(Scanner scanner) {
        System.out.println("Informe a ID para deletar o registro:");
        int id = Integer.parseInt(System.console().readLine());
        funcionarioRepository.deleteById(id);
        System.out.println("Deletado.");
    }
}
