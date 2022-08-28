package com.bonapt.repository.usuario;

import com.bonapt.entitades.Usuario;
import com.bonapt.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioRepositoryQuery {

    Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);
}
