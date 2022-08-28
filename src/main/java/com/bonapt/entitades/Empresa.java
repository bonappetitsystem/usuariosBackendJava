package com.bonapt.entitades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull
    @Size(min = 14, max = 14)
    private String CNPJ;
    @NotNull
    @Column(name = "razao_social")
    private String razaoSocial;
    @NotNull
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    @NotNull
    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;
    @NotNull
    @Column(name = "inscricao_municipal")
    private String inscricaoMunicipal;
    @Embedded
    private Contato contato;
    @Embedded
    private Endereco endereco;
    private String sigla;
    private String senha;
    @Column(name = "data_cadastro", updatable = false)
    private Instant dataCadastro;
    @Column(name = "data_atualizacao")
    private Instant dataAtualizacao;
    private Boolean ativo = true;

    @PrePersist
    private void onInsert(){
        this.dataCadastro = Instant.now();
    }

    @PreUpdate
    private void onUpdate(){
        this.dataAtualizacao = Instant.now();
    }

}
