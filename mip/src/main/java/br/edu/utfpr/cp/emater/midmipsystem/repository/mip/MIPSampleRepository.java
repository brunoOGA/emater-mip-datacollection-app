package br.edu.utfpr.cp.emater.midmipsystem.repository.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MIPSampleRepository extends JpaRepository<MIPSample, Long>{
    
    public Optional<MIPSample> findBySurvey (Survey aSurvey);
    
}
