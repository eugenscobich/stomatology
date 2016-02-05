package md.stomatology.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import md.stomatology.model.Allergy;
import md.stomatology.model.Customer;
import md.stomatology.model.PastIllnesse;
import md.stomatology.model.User;

@Repository
public interface PastIllnesseRepository extends JpaRepository<PastIllnesse, Long> {

	List<PastIllnesse> findByNameIgnoreCaseContainingAndIdNotIn(String query, List<Long> allergyIds);

	List<PastIllnesse> findByIdNotIn(List<Long> allergyIds);

}
