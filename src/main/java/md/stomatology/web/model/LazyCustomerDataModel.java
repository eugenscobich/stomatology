package md.stomatology.web.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import md.stomatology.model.Customer;
import md.stomatology.service.CustomerService;

public class LazyCustomerDataModel extends LazyDataModel<Customer>{
	
	private static final long serialVersionUID = 1L;

	List<Customer> customers;
	
	private CustomerService customerService;
	
	public LazyCustomerDataModel(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@Override
    public Customer getRowData(String rowKey) {
        for(Customer customer : customers) {
            if(customer.getId().equals(rowKey))
                return customer;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Customer customer) {
        return customer.getId();
    }
	
	
	@Override
    public List<Customer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		
		Sort sort = null;
		if (StringUtils.isNotBlank(sortField) && sortOrder != SortOrder.UNSORTED) {
			Direction dir = sortOrder == SortOrder.ASCENDING ? Direction.ASC : Direction.DESC;
			sort = new Sort(dir, sortField);
		}
		int pageIndex = first / pageSize;
		Page<Customer> page = customerService.getCustomers(new PageRequest(pageIndex, pageSize, sort));
		customers = page.getContent(); 
		setRowCount((int) page.getTotalElements());
        return customers; 
    }
	
}
