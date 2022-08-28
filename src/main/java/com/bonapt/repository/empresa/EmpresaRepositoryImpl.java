package com.bonapt.repository.empresa;

import com.bonapt.entitades.Empresa;
import com.bonapt.repository.filter.EmpresaFilter;
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

public class EmpresaRepositoryImpl implements  EmpresaRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Empresa> filtrar(EmpresaFilter empresaFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Empresa> criteria = builder.createQuery(Empresa.class);
        Root<Empresa> root = criteria.from(Empresa.class);

        Predicate[] predicates = createRestrictions(empresaFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Empresa> query = manager.createQuery(criteria);
        addRestrictionsPageable(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(empresaFilter));

    }




    private Predicate[] createRestrictions(EmpresaFilter empresaFilter, CriteriaBuilder builder, Root<Empresa> root) {

        List<Predicate> predicates = new ArrayList<>();
        if(!ObjectUtils.isEmpty(empresaFilter.getNomeFantasia())){
            predicates.add(
                    builder.like(
                            builder.lower(root.get("nomeFantasia")), "%" + empresaFilter.getNomeFantasia().toLowerCase() + "%"
                    )
            );

            if (!ObjectUtils.isEmpty(empresaFilter.getCnpj())) {
                predicates.add(
                        builder.equal(root.get("cnpj"), empresaFilter.getCnpj())
                );
            }

            if (!ObjectUtils.isEmpty(empresaFilter.getRazaoSocial())) {
                predicates.add(
                        builder.like(
                                builder.lower(root.get("razaoSocial")), "%" + empresaFilter.getRazaoSocial().toLowerCase() + "%"
                        )
                );
            }

        }

        return predicates.toArray(new Predicate[0]);
    }

    private void addRestrictionsPageable(TypedQuery<Empresa> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(EmpresaFilter empresaFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Empresa> root = criteria.from(Empresa.class);

        Predicate[] predicates = createRestrictions(empresaFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
