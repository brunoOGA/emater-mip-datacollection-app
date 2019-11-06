package br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperation;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PulverisationOperationRepository extends JpaRepository<PulverisationOperation, Long>{ 

    public Optional<PulverisationOperation> findBySurvey (Survey aSurvey);
}
