package com.bonapt.entitades;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nome;
    @Column(unique = true)
    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @NotNull
    @Column(name = "data_nascimento")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @NotNull
    private String matricula;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Perfil perfil;
    @NotNull
    @Size(min = 6)
    private String senha;
    @NotNull
    private Boolean ativo = true;
    @ManyToOne
    @JoinColumn(name = "id_empresas")
    @NotNull
    private Empresa empresa;
    @Column(name = "data_cadastro", updatable = false)
    private Instant dataCadastro;
    @Column(name = "data_atualizacao")
    private Instant dataAtualizacao;

    @PrePersist
    private void onInsert(){
        this.dataCadastro = Instant.now();
    }

    @PreUpdate
    private void onUpdate(){
        this.dataAtualizacao = Instant.now();
    }

}
