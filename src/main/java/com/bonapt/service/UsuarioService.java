package com.bonapt.service;

import com.bonapt.entitades.Usuario;
import com.bonapt.repository.UsuarioRepository;
import com.bonapt.repository.filter.UsuarioFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<Usuario> pesquisar(UsuarioFilter usuarioFilter, Pageable pageable){

        return usuarioRepository.filtrar(usuarioFilter, pageable);
    }

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(()-> new EmptyResultDataAccessException(1));
    }

    public Usuario cadastrar(Usuario usuario){
       return usuarioRepository.save(usuario);
    }

    public void deletar(Long id){
        buscarPorId(id);
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(Long id, Usuario usuario){
        Usuario usuarioSalvo = buscarPorId(id);
        BeanUtils.copyProperties(usuario, usuarioSalvo, "id");
        return usuarioRepository.save(usuarioSalvo);
    }

    public void atualizarApropriedadeAtivo(Long id, boolean ativo){
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(ativo);
        usuarioRepository.save(usuario);
    }


}
