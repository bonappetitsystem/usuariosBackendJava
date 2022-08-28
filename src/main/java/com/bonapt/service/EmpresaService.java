package com.bonapt.service;

import com.bonapt.entitades.Empresa;
import com.bonapt.repository.EmpresaRepository;
import com.bonapt.repository.filter.EmpresaFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Page<Empresa> pesquisar(EmpresaFilter empresaFilter, Pageable pageable){
        return empresaRepository.filtrar(empresaFilter, pageable);
    }

    public Empresa buscarPorId(Long id){
        return empresaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Empresa cadastrar(Empresa empresa){
        return empresaRepository.save(empresa);
    }

    public void deletar(Long id){
        Empresa empresa = buscarPorId(id);
        empresaRepository.deleteById(id);
    }

    public Empresa atualizar(Long id, Empresa empresa){
        Empresa empresaSalva = buscarPorId(id);
        BeanUtils.copyProperties(empresa, empresaSalva, "id");
        return empresaRepository.save(empresaSalva);
    }

    public void atualizarPropriedadeAtivo(Long id, boolean ativo){
        Empresa empresa = buscarPorId(id);
        empresa.setAtivo(ativo);
        empresaRepository.save(empresa);
    }


}
