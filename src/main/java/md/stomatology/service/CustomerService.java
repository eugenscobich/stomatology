package md.stomatology.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
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
		if (customer.getCreateDate() == null) {
			customer.setCreateDate(new Date());	
		} else {
			customer.setUpdateDate(new Date());
		}
		customerRepository.save(customer);
	}
	
	@PreAuthorize("hasRole('ROLE_DIRECTOR')")
	@Transactional
	public void removeCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

	@Transactional(readOnly = true)
	public Page<Customer> getCustomers(Specifications<Customer> specifications, int page, int size, Sort sort) {
		
		Sort createDateSort = new Sort(Direction.DESC, "createDate");
		sort = sort == null ? createDateSort :	sort.and(createDateSort);
		PageRequest pageRequest = new PageRequest(page, size, sort);
		
		return customerRepository.findAll(specifications, pageRequest);
	}
	
}
