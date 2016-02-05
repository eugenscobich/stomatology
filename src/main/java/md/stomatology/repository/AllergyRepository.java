package md.stomatology.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import md.stomatology.model.Allergy;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

	List<Allergy> findByNameIgnoreCaseContainingAndIdNotIn(String query, List<Long> allergyIds);

	List<Allergy> findByIdNotIn(List<Long> allergyIds);

}
