package md.stomatology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import md.stomatology.model.Visit;

@Repository
public interface VisitRepository extends PagingAndSortingRepository<Visit, Long>, JpaRepository<Visit, Long>, JpaSpecificationExecutor<Visit>  {

}
