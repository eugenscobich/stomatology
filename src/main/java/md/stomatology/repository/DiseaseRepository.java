package md.stomatology.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import md.stomatology.model.Allergy;
import md.stomatology.model.Disease;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

	List<Disease> findByNameIgnoreCaseContainingAndIdNotIn(String query, List<Long> diseaseIds);

	List<Disease> findByIdNotIn(List<Long> diseaseIds);

}
