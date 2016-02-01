package md.stomatology.service;

import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.Authority;
import md.stomatology.model.Customer;
import md.stomatology.model.User;
import md.stomatology.model.type.AuthorityType;
import md.stomatology.repository.CustomerRepository;

@Service("CustomerService")
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
    private AuthenticationManager authenticationManager;
	
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

    public Customer getCustomerById(Long id) {
        return customerRepository.findOne(id);
    }

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
	
}
