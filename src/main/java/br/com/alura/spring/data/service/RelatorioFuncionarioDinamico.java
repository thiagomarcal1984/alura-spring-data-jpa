package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.SpecificationFuncionario;

@Service
public class RelatorioFuncionarioDinamico {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public void inicial() {
        System.out.println("Digite o nome: ");
        String nome = System.console().readLine();

        if (nome.equalsIgnoreCase("NULL")) {
            nome = null;
        }

        System.out.println("Digite o cpf: ");
        String cpf = System.console().readLine();

        if (cpf.equalsIgnoreCase("NULL")) {
            cpf = null;
        }

        System.out.println("Digite o salario: ");
        BigDecimal salario;
        try {
            salario = new BigDecimal(System.console().readLine());
        } catch (NumberFormatException e) {
            salario = BigDecimal.ZERO;
        }

        if (salario == BigDecimal.ZERO) {
            salario = null;
        }
        
        System.out.println("Digite o data de contratacao (formato dd/MM/yyyy): ");
        String data = System.console().readLine();
        LocalDate dataContratacao;
        
        if (data.equalsIgnoreCase("NULL")) {
            dataContratacao = null;
        } else {
            dataContratacao = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        List<Funcionario> funcionarios = funcionarioRepository.findAll(Specification
            .where(
                SpecificationFuncionario.nome(nome))
                .or(SpecificationFuncionario.cpf(cpf))
                .or(SpecificationFuncionario.salario(salario))
                .or(SpecificationFuncionario.dataContratacao(dataContratacao))
        );

        funcionarios.forEach(System.out::println);
    }
}
