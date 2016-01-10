package md.stomatology.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.Customer;
import md.stomatology.repository.CustomerRepository;

@Service("CustomerService")
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

    @PreAuthorize("hasRole('ROLE_EUGEN')")
	@Transactional
	public void addCustomer(Customer customer) {
		customerRepository.save(customer);
	}

    @Transactional
    public void deleteCustomer(Customer customer) {
    	customerRepository.delete(customer);
    }

    @Transactional
    public void updateCustomer(Customer customer) {
    	customerRepository.save(customer);
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findOne((long) id);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
	
}
