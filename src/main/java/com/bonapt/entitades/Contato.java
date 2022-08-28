package com.bonapt.entitades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Embeddable
public class Contato {
    private String telefone;
    private String email;
    private String site;
    private String whatsapp;
}
