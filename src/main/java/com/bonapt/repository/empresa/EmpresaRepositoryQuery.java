package com.bonapt.repository.empresa;

import com.bonapt.entitades.Empresa;
import com.bonapt.repository.filter.EmpresaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmpresaRepositoryQuery {

    Page<Empresa> filtrar(EmpresaFilter empresaFilter, Pageable pageable);
}
