package com.bonapt.controller;

import com.bonapt.entitades.Empresa;
import com.bonapt.repository.filter.EmpresaFilter;
import com.bonapt.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public Page<Empresa> pesquisar(EmpresaFilter empresaFilter, Pageable pageable) {
        return empresaService.pesquisar(empresaFilter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Empresa> cadastrar(@Valid @RequestBody Empresa empresa){
        return ResponseEntity.ok(empresaService.cadastrar(empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> deletar(@PathVariable Long id){
        empresaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @Valid @RequestBody Empresa empresa){
        return ResponseEntity.ok(empresaService.atualizar(id, empresa));
    }

    @PutMapping("/{id}/ativo")
    public ResponseEntity<Empresa> atualizarPropriedadeAtivo(@PathVariable Long id, @Valid @RequestBody boolean ativo){
        empresaService.atualizarPropriedadeAtivo(id, ativo);
        return ResponseEntity.noContent().build();
    }
}
