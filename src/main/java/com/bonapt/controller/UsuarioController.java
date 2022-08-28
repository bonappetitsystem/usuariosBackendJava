package com.bonapt.controller;

import com.bonapt.entitades.Usuario;
import com.bonapt.repository.UsuarioRepository;
import com.bonapt.repository.filter.UsuarioFilter;
import com.bonapt.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Page<Usuario> buscarTodos(UsuarioFilter usuarioFilter, Pageable pageable) {
        return usuarioService.pesquisar(usuarioFilter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deletar(@PathVariable Long id){
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
    }

    @PutMapping("/{id}/ativo")
    public ResponseEntity<Usuario> atualizarPropriedadeAtivo(@PathVariable Long id, @Valid @RequestBody boolean ativo){
        usuarioService.atualizarApropriedadeAtivo(id, ativo);
        return ResponseEntity.noContent().build();
    }
}
