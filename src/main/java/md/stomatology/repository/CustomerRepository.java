package md.stomatology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import md.stomatology.model.Customer;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, JpaRepository<Customer, Long> {

}
