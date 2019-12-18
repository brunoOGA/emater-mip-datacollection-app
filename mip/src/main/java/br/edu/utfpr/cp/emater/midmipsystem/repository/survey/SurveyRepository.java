package br.edu.utfpr.cp.emater.midmipsystem.repository.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query(
            value = "SELECT * FROM survey \n" +
                    "	INNER JOIN field \n" +
                    "		ON survey.field_id = field.id \n" +
                    "	INNER JOIN city\n" +
                    "		ON field.city_id = city.id\n" +
                    "	INNER JOIN region_cities\n" +
                    "		ON city.id = region_cities.cities_id\n" +
                    "	WHERE region_cities.region_id = :aRegionId",
            nativeQuery = true)
    public List<Survey> findByRegionId(@Param("aRegionId") Long aRegionId);
}
