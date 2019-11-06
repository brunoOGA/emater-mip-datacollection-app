package br.edu.utfpr.cp.emater.midmipsystem.repository.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long>{
    
    public Optional<Field> findByFarmer (Farmer aFarmer);
    
}
