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
		
		SpecificationFilter<Visit> filterByCustomerId = new SpecificationFilter<>("customer", "=", customerId, Visit.class, "toothInfos");
		
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
		initVisit(visit);
		return visit;
	}

	private void initVisit(Visit visit) {
		List<ToothInfo> toothInfos = visit.getToothInfos();
		
		ToothInfo[][] toothInfosToShow = new ToothInfo[4][8];
		if (toothInfos != null) {
			for (ToothInfo toothInfo : toothInfos) {
				toothInfosToShow[toothInfo.getToothQuadrant() - 1][toothInfo.getToothIndex() - 1] = toothInfo;
			}
		}
		
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 7; j++) {
				if (toothInfosToShow[i][j] == null) {
					ToothInfo toothInfo = new ToothInfo();
					toothInfo.setToothQuadrant(i + 1);
					toothInfo.setToothIndex(j + 1);
					toothInfosToShow[i][j] = toothInfo;
				}
			}
		}
		
		List<ToothInfo> topToothInfos = new ArrayList<>();
		for (int k = 7; k >= 0; k--) {
			topToothInfos.add(toothInfosToShow[0][k]);
		}
		
		for (int k = 0; k <= 7; k++) {
			topToothInfos.add(toothInfosToShow[1][k]);
		}
		visit.setTopToothInfos(topToothInfos);
		
		List<ToothInfo> bottomToothInfos = new ArrayList<>();
		for (int k = 7; k >= 0; k--) {
			bottomToothInfos.add(toothInfosToShow[3][k]);
		}
		
		for (int k = 0; k <= 7; k++) {
			bottomToothInfos.add(toothInfosToShow[2][k]);
		}
		
		visit.setBottomToothInfos(bottomToothInfos);
		
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
		initVisit(visit);
		return visit;
	}

	@Transactional
	public void saveVisit(Visit visit) {
		if (visit.getVisitDate() == null) {
			visit.setVisitDate(new Date());
		} else {
			visit.setUpdateDate(new Date());
		}
		for (ToothInfo toothInfo : visit.getAllToothInfos()) {
			if (toothInfo.getId() != null && toothInfo.getId().equals(0l)) {
				toothInfo.setId(null);
				if (visit.getToothInfos() == null) {
					visit.setToothInfos(new ArrayList<>());
				} 
				visit.getToothInfos().add(toothInfo);
			}
		}
		
		visitRepository.save(visit);
	}

	@PreAuthorize("hasRole('ROLE_DIRECTOR')")
	@Transactional
	public void removeVisit(Visit visit) {
		visitRepository.delete(visit);
	}
	
}
