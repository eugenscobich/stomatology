package md.stomatology.web.bean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

import md.stomatology.model.Allergy;
import md.stomatology.model.Customer;
import md.stomatology.model.PastIllnesse;
import md.stomatology.model.User;
import md.stomatology.model.Visit;
import md.stomatology.repository.specification.SpecificationFilter;
import md.stomatology.service.AllergyService;
import md.stomatology.service.CustomerService;
import md.stomatology.service.PastIllnesseService;
import md.stomatology.service.VisitService;
import md.stomatology.util.WebUtil;
import md.stomatology.web.bean.CustomerListBean.LazyCustomerDataModel;

@ManagedBean
@ViewScoped
@URLMapping(id = "view-customer", pattern = "/view-customer/#{ /[1-9][0-9]*/ customerId : viewCustomerBean.customerId}", viewId = "/WEB-INF/views/pages/secure/view-customer.xhtml")
public class ViewCustomerBean implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(LoginBean.class);

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{customerService}")
	private transient CustomerService customerService;
	
	@ManagedProperty(value = "#{visitService}")
	private transient VisitService visitService;

	private Long customerId;

	private Customer customer;

	private LazyDataModel<Visit> visits;
	
	@URLAction(onPostback = false)
	public String loadCustomer() {
		LOG.info("loadCustomer");
		if (customerId != null) {
			try {
				customer = customerService.getCustomerById(customerId);
				visits = new LazyVisitDataModel();
			} catch (Exception e) {
				WebUtil.addErrorMessage("could-not-load-customer-details", "error", e.getMessage());
				return "pretty:customer-list";
			}
			return null;
		}
		WebUtil.addWarnMessage("could-not-load-customer-details", "");
		return "pretty:customer-list";
	}
	
	public String removeVisit(Visit visit) {
		try {
			visitService.removeVisit(visit);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			WebUtil.addErrorMessage("could-not-remove-visit", "error", new String[] { e.getMessage() });
			return "";
		}
		WebUtil.addSuccessMessage("visit-has-removed-successfully");
		return "";
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	
	public VisitService getVisitService() {
		return visitService;
	}

	public void setVisitService(VisitService visitService) {
		this.visitService = visitService;
	}


	public LazyDataModel<Visit> getVisits() {
		return visits;
	}

	public void setVisits(LazyDataModel<Visit> visits) {
		this.visits = visits;
	}


	public class LazyVisitDataModel extends LazyDataModel<Visit>{
		
		private static final long serialVersionUID = 1L;

		List<Visit> visits;
		
		@Override
	    public List<Visit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
			
			Sort sort = null;
			if (StringUtils.isNotBlank(sortField) && sortOrder != SortOrder.UNSORTED) {
				Direction dir = sortOrder == SortOrder.ASCENDING ? Direction.ASC : Direction.DESC;
				sort = new Sort(dir, sortField);
			}
			
			int pageIndex = first / pageSize;
			
			Specifications<Visit> specifications = null;
			
			Iterator<Entry<String, Object>> filterIterator = null;
			if (filters.size() > 0) {
				filterIterator = filters.entrySet().iterator();
				Entry<String, Object> entry = filterIterator.next();
				specifications = Specifications.where(new SpecificationFilter<>(entry.getKey(), "like", entry.getValue()));
			}
			
			if (filterIterator != null) {
				for (; filterIterator.hasNext();) {
					Entry<String, Object> entry = filterIterator.next();
					specifications = specifications.and(new SpecificationFilter<>(entry.getKey(), "like", entry.getValue()));
				}
			}
			
			Page<Visit> page = visitService.getVisitsByCustomerd(customer.getId(), specifications, pageIndex, pageSize, sort);
			visits = page.getContent(); 
			setRowCount((int) page.getTotalElements());
	        return visits; 
	    }
		
	}
	
}
