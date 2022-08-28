package com.bonapt.repository;

import com.bonapt.entitades.Empresa;
import com.bonapt.repository.empresa.EmpresaRepositoryImpl;
import com.bonapt.repository.empresa.EmpresaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, EmpresaRepositoryQuery {
}
