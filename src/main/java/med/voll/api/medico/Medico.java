package med.voll.api.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;

@Table (name = "medicos")
@Entity (name= "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Medico {

    // Define a estratégia de geração automática da chave primária.
    //O IDENTITY diz para o banco usar auto-incremento.
    // Ou seja, o banco gera o valor do id automaticamente (ex: 1, 2, 3...).
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    //Diz que o enum Especialidade
    // será salvo como texto (string) no banco, e não como número (ordinal).
    @Enumerated(EnumType.STRING)
    private  Especialidade especialidade;

    //O que faz: Indica que o campo endereco é uma classe embutida (comum em composição).
    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.id=dados.id();
        this.nome=dados.nome();
        this.crm=dados.crm();
        this.email=dados.email();
        this.telefone= dados.telefone();
        this.endereco=new Endereco(dados.endereco());
        this.especialidade=dados.especialidade();
    }

    public String getNome(){
        return this.nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCrm() {
        return crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Long getId(){
        return id;
    }

    public void atualizarInformacoes(@Valid DadosAtualizarMedicos dadosAtualizarMedicos) {
        if (dadosAtualizarMedicos.nome() != null){
            this.nome=dadosAtualizarMedicos.nome();
        }

        if (dadosAtualizarMedicos.telefone() != null){
            this.telefone = dadosAtualizarMedicos.telefone();
        }

        if (dadosAtualizarMedicos.dadosEndereco() != null){
            this.endereco.atualizarInform(dadosAtualizarMedicos.dadosEndereco());
        }

    }


    public void excluir() {
        this.ativo=false;
    }
}
