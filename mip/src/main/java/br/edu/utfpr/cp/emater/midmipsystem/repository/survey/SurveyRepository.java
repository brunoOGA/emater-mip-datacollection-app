package br.edu.utfpr.cp.emater.midmipsystem.repository.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query(
            value = "SELECT * FROM survey \n"
            + "	INNER JOIN field \n"
            + "		ON survey.field_id = field.id \n"
            + "	INNER JOIN city\n"
            + "		ON field.city_id = city.id\n"
            + "	INNER JOIN region_cities\n"
            + "		ON city.id = region_cities.cities_id\n"
            + "	WHERE region_cities.region_id = :aRegionId",
            nativeQuery = true)
    public List<Survey> findByRegionId(@Param("aRegionId") Long aRegionId);

    @Query(
            value = "SELECT "
            + "survey.id,\n"
            + "survey.created_at,\n"
            + "survey.last_modified,\n"
            + "survey.emergence_date,\n"
            + "survey.harvest_date,\n"
            + "survey.sowed_date,\n"
            + "survey.bt,\n"
            + "survey.cultivar_name,\n"
            + "survey.rust_resistant,\n"
            + "survey.latitude,\n"
            + "survey.longitude,\n"
            + "survey.collector_installation_date,\n"
            + "survey.spore_collector_present,\n"
            + "survey.productivity_farmer,\n"
            + "survey.productivity_field,\n"
            + "survey.separated_weight,\n"
            + "survey.application_cost_currency,\n"
            + "survey.soya_price,\n"
            + "survey.plant_per_meter,\n"
            + "survey.total_area,\n"
            + "survey.total_planted_area,\n"
            + "survey.created_by_id,\n"
            + "survey.modified_by_id,\n"
            + "survey.field_id,\n"
            + "survey.harvest_id,\n"
            + "survey.closing_date FROM survey \n"
            + "   INNER JOIN field_supervisors \n"
            + "       ON survey.field_id = field_supervisors.field_id \n"
            + "   WHERE field_supervisors.supervisors_id = :aSupervisorId",
            nativeQuery = true
    )
    public List<Survey> findBySupervisorId(@Param("aSupervisorId") Long supervisorId);
}
