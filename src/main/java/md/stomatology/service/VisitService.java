package md.stomatology.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.Authority;
import md.stomatology.model.Customer;
import md.stomatology.model.ToothInfo;
import md.stomatology.model.User;
import md.stomatology.model.Visit;
import md.stomatology.model.type.AuthorityType;
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
		Visit visit = visitRepository.findOne(visitId);
		List<ToothInfo> toothInfos = visit.getToothInfos();
		
		List<ToothInfo> toothInfosToShow = new ArrayList<>();
		
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 8; j++) {
				ToothInfo savedToothInfo = null;
				for (ToothInfo toothInfo : toothInfos) {
					if (toothInfo.getToothQuadrant() == i && toothInfo.getToothIndex() == j) {
						savedToothInfo = toothInfo;
						break;
					}
				}
				
				
			}
		}
		
		
		return visit;
	}

	@Transactional(readOnly = true)
	public Visit createNewVisit(Long customerId, User currentUser) {
		Customer customer = customerService.getCustomerById(customerId);
		Visit visit = new Visit();
		visit.setCustomer(customer);
		
		Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
		boolean isDentist = authorities.stream().anyMatch(x -> x.getAuthority().equals(AuthorityType.ROLE_DENTIST.toString()));

		if (isDentist) {
			visit.setDentist(currentUser);
		}
		
		visit.setVisitDate(new Date());
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

	@PreAuthorize("hasRole('ROLE_DIRECTOR')")
	@Transactional
	public void removeVisit(Visit visit) {
		visitRepository.delete(visit);
	}
	
}
