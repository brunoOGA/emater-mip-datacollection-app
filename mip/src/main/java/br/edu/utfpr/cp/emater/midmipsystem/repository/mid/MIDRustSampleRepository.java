package br.edu.utfpr.cp.emater.midmipsystem.repository.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MIDRustSampleRepository extends JpaRepository<MIDRustSample, Long> {

    public List<MIDRustSample> findBySurvey(Survey aSurvey);
}
