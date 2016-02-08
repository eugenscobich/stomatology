package md.stomatology.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.Customer;
import md.stomatology.model.Visit;
import md.stomatology.repository.VisitRepository;
import md.stomatology.repository.specification.SpecificationFilter;

@Service
public class VisitService {

	@Autowired
	private VisitRepository visitRepository;

	@Autowired
	private CustomerService customerService;

	@Transactional(readOnly = true)
	public Page<Visit> getVisitsByCustomerd(Long customerId, Specifications<Visit> specifications, int page, int size, Sort sort) {
		
		Sort createDateSort = new Sort(Direction.DESC, "visitDate");
		sort = sort == null ? createDateSort :	sort.and(createDateSort);
		PageRequest pageRequest = new PageRequest(page, size, sort);
		
		SpecificationFilter<Visit> filterByCustomerId = new SpecificationFilter<>("customer", "=", customerId);
		
		if (specifications != null) {
			specifications = specifications.and(filterByCustomerId);
		} else {
			specifications = Specifications.where(filterByCustomerId);
		}
		
		return visitRepository.findAll(specifications, pageRequest);
	}

	@Transactional(readOnly = true)
	public Visit getVisitById(Long visitId) {
		return visitRepository.findOne(visitId);
	}

	@Transactional(readOnly = true)
	public Visit createNewVisit(Long customerId) {
		Customer customer = customerService.getCustomerById(customerId);
		Visit visit = new Visit();
		visit.setCustomer(customer);
		return visit;
	}

	@Transactional
	public void saveVisit(Visit visit) {
		if (visit.getVisitDate() == null) {
			visit.setVisitDate(new Date());
		} else {
			visit.setUpdateDate(new Date());
		}
		visitRepository.save(visit);
	}
	
}
