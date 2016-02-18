package md.stomatology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import md.stomatology.model.RememberMeToken;

@Repository
public interface RememberMePersistentTokenRepository extends JpaRepository<RememberMeToken, Long>  {

	RememberMeToken findBySeries(String seriesId);

	Long removeByUsername(String username);

}
