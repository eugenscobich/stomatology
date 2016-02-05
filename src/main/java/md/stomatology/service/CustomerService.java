package md.stomatology.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.Authority;
import md.stomatology.model.Customer;
import md.stomatology.model.User;
import md.stomatology.model.type.AuthorityType;
import md.stomatology.repository.CustomerRepository;

@Service("customerService")
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

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long customerId) {
    	Customer customer = customerRepository.findOne(customerId);
    	customer.getAllergies().size();
    	customer.getPastIllnesses().size();
        return customer;
    }
    
    @Transactional(readOnly = true)
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

	public Customer createNewCustomer(User currentUser) {
		Customer customer = new Customer();
		
		Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
		boolean isDentist = authorities.contains(new Authority(AuthorityType.ROLE_DENTIST));
		
		if (isDentist) {
			customer.setDentist(currentUser);
		}
		
		return customer;
	}

	@Transactional
	public void saveCustomer(Customer customer) throws Exception {
		customerRepository.save(customer);
	}

	@Transactional
	public void removeCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

	@Transactional(readOnly = true)
	public Page<Customer> getCustomers(PageRequest pageRequest) {
		return customerRepository.findAll(pageRequest);
	}
	
}
