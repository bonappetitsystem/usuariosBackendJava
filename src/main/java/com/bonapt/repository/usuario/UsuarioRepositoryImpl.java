package com.bonapt.repository.usuario;

import com.bonapt.entitades.Usuario;
import com.bonapt.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
        Root<Usuario> root = criteria.from(Usuario.class);
        Predicate[] predicates = createRestrictions(usuarioFilter, builder, root);
        criteria.where(predicates);
        TypedQuery<Usuario> query = manager.createQuery(criteria);
        addRestrictionsPageable(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(usuarioFilter));
    }




    private Predicate[] createRestrictions(UsuarioFilter usuarioFilter, CriteriaBuilder builder, Root<Usuario> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(usuarioFilter.getCpf())) {
            predicates.add(
                    builder.like(
                            builder.lower(root.get("cpf")), "%" + usuarioFilter.getCpf().toLowerCase() + "%"
                    ));
        }

        if (!ObjectUtils.isEmpty(usuarioFilter.getNome())) {
            predicates.add(
                    builder.like(
                            builder.lower(root.get("nome")), "%" + usuarioFilter.getNome().toLowerCase() + "%"
                    ));
        }

        if (!ObjectUtils.isEmpty(usuarioFilter.getPerfil())){
            predicates.add(
                    builder.equal(
                            builder.lower(root.get("perfil").as(String.class)), usuarioFilter.getPerfil().toLowerCase()
                    ));
        }



        return predicates.toArray(new Predicate[0]);
    }

    private void addRestrictionsPageable(TypedQuery<Usuario> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(UsuarioFilter usuarioFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Usuario> root = criteria.from(Usuario.class);

        Predicate[] predicate = createRestrictions(usuarioFilter, builder, root);
        criteria.where(predicate);
        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}