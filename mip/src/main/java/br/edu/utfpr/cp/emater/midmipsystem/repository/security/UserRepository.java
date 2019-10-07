package br.edu.utfpr.cp.emater.midmipsystem.repository.security;

import br.edu.utfpr.cp.emater.midmipsystem.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { 
    
    User findByUsername (String username);
    
}
