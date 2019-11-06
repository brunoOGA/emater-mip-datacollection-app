package br.edu.utfpr.cp.emater.midmipsystem.repository.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MIDRustSampleRepository extends JpaRepository<MIDRustSample, Long>{

    public Optional<MIDRustSample> findBySurvey (Survey aSurvey);
}
