package br.edu.utfpr.cp.emater.midmipsystem.repository.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Cultivar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CultivarRepository extends JpaRepository<Cultivar, Long> { }
