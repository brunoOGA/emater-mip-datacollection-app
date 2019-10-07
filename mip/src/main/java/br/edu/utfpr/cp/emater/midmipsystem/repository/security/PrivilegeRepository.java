package br.edu.utfpr.cp.emater.midmipsystem.repository.security;

import br.edu.utfpr.cp.emater.midmipsystem.entity.security.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> { }
