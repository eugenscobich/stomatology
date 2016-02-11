package md.stomatology.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import md.stomatology.model.Treatment;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

	List<Treatment> findByNameIgnoreCaseContainingAndIdNotIn(String query, List<Long> treatmentIds);

	List<Treatment> findByIdNotIn(List<Long> treatmentIds);

}
