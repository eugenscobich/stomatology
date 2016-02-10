package md.stomatology.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import md.stomatology.model.User;
import md.stomatology.model.type.AuthorityType;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	@Query("SELECT DISTINCT u FROM User u INNER JOIN u.authorities a WHERE u.name LIKE :query OR u.surname LIKE :query OR u.username LIKE :query AND a.authority=:authority AND u.id <> :userId")
	List<User> findAllDentists(@Param("query") String query, @Param("authority") AuthorityType authorityType, @Param("userId") Long userId);
	
	
	@Query("SELECT DISTINCT u FROM User u INNER JOIN u.authorities a WHERE a.authority=:authority AND u.id <> :userId")
	List<User> findAllDentists(@Param("authority") AuthorityType authorityType, @Param("userId") Long userId);
	
}
