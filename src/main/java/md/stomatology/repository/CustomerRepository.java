package md.stomatology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import md.stomatology.model.Customer;
import md.stomatology.model.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
